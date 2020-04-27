package com.skovdev.springlearn.repository;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.constraints.NotNull;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Objects;

@Repository
@Slf4j
public class AwsS3FileStorage implements FilesRepository {

    private final WebClient webClient;
    private final String avatarsBucketName;
    private final String avatarsThumbnailBucketName;
    private final String s3url;
    private final String accessKey;
    private final String secretAccessKey;

    //TODO move from @Value to @ConfigurationProperties https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-external-config-java-bean-binding
    public AwsS3FileStorage(WebClient.Builder webClientBuilder,
                            @Value("${aws.s3.url}") @NotNull String s3url,
                            @Value("${aws.s3.bucket.avatarsFullsize}") @NotNull String avatarsBucketName,
                            @Value("${aws.s3.bucket.avatarsThumbnail}") @NotNull String avatarsThumbnailBucketName,
                            @Value("${aws.s3.accessKey}") @NotNull String accessKey,
                            @Value("${aws.s3.secretAccessKey}") @NotNull String secretAccessKey) {
        this.avatarsBucketName = Objects.requireNonNull(avatarsBucketName, "No value found for mandatory property aws.s3.bucket.avatarsFullsize. Please, specify it in config");
        this.avatarsThumbnailBucketName = Objects.requireNonNull(avatarsThumbnailBucketName, "No value found for mandatory property aws.s3.bucket.avatarsThumbnail. Please, specify it in config");
        this.s3url = Objects.requireNonNull(s3url);
        this.accessKey = Objects.requireNonNull(accessKey);
        this.secretAccessKey = Objects.requireNonNull(secretAccessKey);
        this.webClient = webClientBuilder.build();
    }

    @Override
    public SaveImageResponse saveImageWithThumbnail(@NotNull MultipartFile file) {
        Objects.requireNonNull(file);
        //TODO delete previous avatar to save space
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFilenameWithExtension = generateRandomFilename() + "." + fileExtension;

        Mono<ResponseEntity<String>> responseAvatarSave = buildPublicFileUploadRequest(accessKey,
                secretAccessKey,
                avatarsBucketName,
                newFilenameWithExtension,
                file.getContentType(),
                s3url)
                .body(BodyInserters.fromResource(file.getResource())).retrieve().toEntity(String.class);
        responseAvatarSave.block();

        byte[] thumbnail = createThumbnail(file);
        Mono<ResponseEntity<String>> responseThumbnailSave = buildPublicFileUploadRequest(accessKey,
                secretAccessKey,
                avatarsThumbnailBucketName,
                newFilenameWithExtension,
                file.getContentType(),
                s3url)
                .bodyValue(thumbnail).retrieve().toEntity(String.class);
        responseThumbnailSave.block();

        return new SaveImageResponse(
                URI.create(createFileUrl(avatarsBucketName, s3url, newFilenameWithExtension)),
                URI.create(createFileUrl(avatarsThumbnailBucketName, s3url, newFilenameWithExtension))
        );

    }

    private String generateRandomFilename() {
        return RandomStringUtils.randomAlphanumeric(16, 24);
    }

    @SneakyThrows
    private byte[] createThumbnail(MultipartFile originalImage) {
        ByteArrayOutputStream thumbnailOutputStream = new ByteArrayOutputStream();

        try (InputStream is = originalImage.getInputStream()) {
            Thumbnails.of(is).crop(Positions.CENTER).size(200, 200).useOriginalFormat().toOutputStream(thumbnailOutputStream);
        } catch (Exception e) {
            try {
                thumbnailOutputStream.close();
            } catch (Exception closeEx) {
                e.addSuppressed(closeEx);
            }
            throw e;
        }
        byte[] bytes = thumbnailOutputStream.toByteArray();
        thumbnailOutputStream.close();
        return bytes;
    }

    @SneakyThrows
    private WebClient.RequestBodySpec buildPublicFileUploadRequest(String awsS3AccessKey,
                                                                   String awsS3SecretAccessKey,
                                                                   String bucketName,
                                                                   String fileNameWithExtension,
                                                                   String contentType,
                                                                   String s3BaseUrl) {
        String httpVerb = "PUT";
        String canonicalizedResource = "/" + bucketName + "/" + fileNameWithExtension;
        String date = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss Z"));
        String xAmzAcl = "public-read";
        String canonicalizedAmzHeaders = "x-amz-acl:" + xAmzAcl + "\n";

        String stringToSign = httpVerb + "\n\n" +
                contentType + "\n" +
                date + "\n" +
                canonicalizedAmzHeaders +
                canonicalizedResource;

        Mac hmac = Mac.getInstance("HmacSHA1");

        hmac.init(new SecretKeySpec(awsS3SecretAccessKey.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));
        String signature = Base64.getEncoder().encodeToString(
                hmac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8)))
                .replaceAll("\n", "");

        String authorizationString = "AWS " + awsS3AccessKey + ":" + signature;

        WebClient.RequestBodySpec prebuiltRequest = webClient.put().uri(createFileUrl(bucketName, s3BaseUrl, fileNameWithExtension))
                .contentType(MediaType.parseMediaType(contentType))
                .header("x-amz-acl", xAmzAcl)
                .header("Date", date)
                .header("Authorization", authorizationString);
        return prebuiltRequest;
    }

    private String createFileUrl(String bucketName, String s3BaseUrl, String fileNameWithExtension) {
        return "https://" + bucketName + "." + s3BaseUrl + "/" + fileNameWithExtension;
    }

}
