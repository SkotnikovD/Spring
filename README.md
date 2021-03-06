Привет, добро пожаловать на страничку моего pet-project, который помогает мне изучать Spring Framework.
Помимо бэка для этого проекта я пишу и [фронт](https://github.com/SkotnikovD/springlearn-fe), его не судите строго, я все-таки не фронтэндщик.  
Поиграться: https://www.springlearn.ml
# Описание
Приложение позволяет пользователю просматривать посты других пользователей, а также, пройдя регистрацию, оставлять собственные посты.  
Документация API: [Swagger](http://34.242.249.184:8080/swagger-ui.html)
# Основная функциональность
* Регистрация/вход при помощи логина и пароля
* Регистрация/вход при помощи Google аккаунта
* Просмотр общего списка постов
* Написание собственного поста
* Удаление любого поста администратором
# Скриншоты
<img src="https://avatars-fullsize.s3-eu-west-1.amazonaws.com/Main.png" title="Список постов" width="500" height="250" /> <img src="https://avatars-fullsize.s3-eu-west-1.amazonaws.com/Signup.png" title="Регистрация" width="500" height="250" /> 
# Технологии
Spring Boot, Spring Core, Spring Security, Spring MVC;  
Spring Data JDBC, Spring Data JPA, Liquibase, PostgreSQL;  
Spring Testing, JUnit, Mockito;  
Swagger;  
Git, Docker, Logback;  
AWS EC2 Linux2 AMI, AWS S3, AWS RDS, AWS CloudFront CDN;   
Фронт: Angular 10.
# Детали реализации
## Слой данных
#### JDBC
Сперва я хотел вспомнить чистый JDBC, использовал JdbcTemplate c PreparedStatement и NamedParameterJdbcTemplate, ResultSetExtractor, RowMapper.  
Затем изучал и смотрел в сторону Spring JDBC Data. Интересный проект, кажется его точно стоит использовать, если в проекте присутствует множество простых CRUD операций над единичными сущностями. Если в основном придется использовать хитрые запросы, то тут выбор не очевиден, нужно глубже изучать, все-таки Data JDBC накладывает и ряд ограничений, возможно будет проще на обычном JDBC делать. Ну и если работать нужно с глубокими, сложными иерархиями объектов, то тут я бы в сторону ORM смотрел.
#### JPA
Затем я все переписал с JDBC на JPA (Hibernate). Решил использовать Data JPA, их JpaRepository, @Query JPQL, запросы на основе method name (прикольная штука для простых запросов)   
В целом я люблю ORM, но чтобы по-настоящему эффективно и без косяков с ним работать, я считаю, что его нужно реально глубоко изучить, прочитав пару книгу, полностью посвященных этому подходу. Даже в моем проекте столкнулся с несколькими интересными кейсами:  
>>* Cтолкнулся с интересным side effect при переходе с JDBC: в JDBC я обернул вызов сохранения пользователя в базу в try-catch, чтобы отлавливать unique constraint violation когда пытаемся создать уже существующего пользователя, и бросать свое кастомное исключение, которое потом замапится на 400 статус. После переходе на JPA этот вызов бросать исключение перестал, т.к. реального взаимодействия с БД не происходило, так как явно flush я не вызывал. И исключение летело уже в другом месте, когда я пытался получить только что созданный объект по его id и EntityManager делал flush. 
>>* Еще был интересный случай, когда перешел на JPA: создаешь например пользователя в БД вручную, потом пробуешь создать через приложение и получаешь pk unique constraint violation. Выяснилось, что по умолчанию Hibernate кэширует для себя 50 idшников, чтобы не ходить в базу каждый раз при вставке. Но вот сиквенс в базе при этом увеличивает только на 1. Понятно к чему это ведет при параллельном использовании базы. 
>>* Еще интересная ситуация была, когда я решил включить валидацию схемы БД на соответствие JPA сущностям при запуске приложения. Проверка не проходила. Потребовалось немало усилий, чтобы найти информацию о том, что валидатору не нравятся pk, созданные как GENERATED BY DEFAULT AS IDENTITY. Hibernate работает с ним корректно, но вот валидацию не проходит. Баг заведен, PR сделан, но в релиз фикс пока не вошел, пришлось вручную фиксить, добавляя [кастомный SQL-диалект](https://github.com/SkotnikovD/Spring/commit/efb50108e2cd087bbbebbf5061faac3bc09a4dd7)  
>>* Ну и N+1)  
>>И таких нюансов большое количество, они могут приводить к очень неочевидным багам, и не всегда их легко отловить и пофиксить. В этом плане JDBC конечно куда более предсказуем.  

К JPA прикрутил еще библиотеку https://github.com/Cosium/spring-data-jpa-entity-graph, чтобы можно было для одного query-метода (например find) задавать нужную глубину объектного графа в зависимости от сценария.  
Базу версионирую при помощи Liquibase.   
## Безопасность
Изучив современные подходы к авторизации/аутентификации, остановился на **HTTP Bearer Authorization**. В качестве Bearer использовал JWT с закодированной информацией о пользователе. Это позволяет не делать лишний запрос в БД для извлечения пользователя на каждый REST-вызов, требующий авторизации. Мне кажется это здорово. Такую авторизацию крайне просто и на клиенте использовать. Но конечно проблема в том, что токен при такой схеме не отозвать. Но если захотим такую функциональность, то можно прикрутить таблицу, которая будет хранить отозванные токены (типа black list) и полностью кэшировать ее в памяти (она ведь будет не большая). И можно с ней сверяться прежде чем авторизовать пользователя.  
Сейчас бы я все-таки настроил полноценный OAuth, благо в Boot это весьма просто сделать. Все-таки в нем и refresh токены есть, да и выделенный authorization server хорошо ложится на микросервисы. 

#### Роли
Пользователи имеют роли. Например удалять посты может только администратор. Для серьезных решений я бы конечно выбрал более сложную ролевую модель на основе permissions, которые на роли навешиваются.  
CORS открыл для всех для простоты, про CSRF в курсе, разбирался с preflight запросами и т.д. Но для моего сервиса не очень актуально, разве что пост можно разместить - не страшно.

#### Регистрация при помощи Google
Здесь выбрал OAuth flow при котором клиент получает от гугла токен авторизации, передает его серверу, который уже сам валидирует его при помощи Google SDK и достает информацию о пользователе. Изучив документацию об уязвимостях от OWASP, сделал заключение, что такой вариант не обладает критическими уязвимостями (смущало, что клиентский секрет будет открыто храниться на клиенте). Да и в Google настраивается Authorized JavaScript origins, поэтому не удастся использовать этот секрет в сторонних фронтэндах. 

## Валидация входных данных
Входные данные от клиента валидирую через @Validate и аннтирование полей DTO моделек.

## Обработка ошибок
Хотел сделать так, чтобы все ошибки были одинакового формата. Создал ResponseEntityExceptionHandler, там обрабатываю все кастомные исключения, а также перехватываю просто все Exception. Но этого оказалось недостаточно, потому что этот хэндлер не отрабатывает исключения, которые произошли не в контроллере и позже, а на ранних этапах, например в filter chain. Для отлова таких исключений [заимплементил ErrorController](https://github.com/SkotnikovD/Spring/blob/master/src/main/java/com/skovdev/springlearn/error/handler/ErrorEndpointController.java).     
При помощи @JsonFilter сделал возможность убрать стэктрейс на прод среде (среды разделены при помощи профилей)

## Пагинация и бесконечная прокрутка
Для списка постов реализована бесконечная прокрутка с автоматической подгрузкой. Для этого нужна была пагинация. Встроенный Спринговый Pageable не подходил, потому что реализует пагинацию на основе offset. Такая пагинация подошла бы для данных, которые не изменяются в процессе пагинации. Посты же могут добавляться другими пользователями и при пагаинации будут появляться дубликаты, поскольку offset будет смещаться. Была идея убирать дубликаты на фронте, но выглядит как грязный хак, поэтому я реализовал пагинацию по курсору.

## Аватарки и AWS S3
При регистрации пользователь может загрузить аватарку.   
Для хранения аватарок я выбрал S3 хранилище. Из него потом и через CDN очень просто их раздавать. В процессе сохранения создавал уменьшенную копию аватарки, но и оригинал сохранял. Мне хотелось поработать с WebClient, поэтому я решил не тащить Amazon SDK в зависимости (с его помощью загрузка файлов в S3 не представляет труда, SDK берет всю работу по авторизации) и загружал картинки в S3 по REST. Это оказалось не очень простой задачей, так как запрос должен содержать кучу дополнительной информации, быть подписан секретным ключом.  
Загрузку аватарки вынес отдельным рестом, поэтому пользователь регистрируется мгновенно, а в фоне происходит уже загрузка аватарки. Она автоматически появляется по завершении загрузки.

## Тестирование
У меня не было цели сделать какое-то достойное тестовое покрытие, хотелось изучить больше новых технологий. Поэтому для демонстрации я сделал только пару модульных тестов, и интеграционный тест для базы. Использовал @DataJpaTest чтобы не поднимать за зря еще и вебовскую часть. Использовал in-memory БД OpenTable (по сути in-memory Postgre). В будущем хотел посмотреть в сторону Test containers.

## Деплоймент
Деплоймент пока не автоматизированный: идешь на сервер, pullишь main, запускаешь скрипт сборки докер. Сервер слабенький, еле собирает, поэтому секйчас думаю было бы удобнее сделать через Docker Hub. В процессе создания имеджа использовал multistage build - удобная штука.
На этом же сервере крутится и фронт. 
#### HTTPS
Я не хотел вшивать SSL в сам сервер, потому что сразу же возникает проблема хранения секретных ключей, да и сервер берет на себя еще и дополнительную работу, связанную с шифрацией. Поэтому я посчитал логичным использовать TLS termination proxy (неожиданно в лице AWS CloudFront, потому что он бесплатный, так бы конечно Elastic Load Balancer использовал). Сертификат получал у Let's Encrypt, через консольный Cerbot - то еще приключение). У меня не было возможности в моем халявном DNS создавать TXT записи, поэтому подтверждать владение доменом пришлось при помощи развертывания Nginx в AWS в режиме раздачи static content и через него выставлять код валидации от Let's Encrypt)  

На этом пока все, спасибо что проявили интерес к моей работе)
