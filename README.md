# Discovery-Server
# 1. Обзор сервиса
`Discovery Service` — это реализация `Netflix Eureka Server`, обеспечивающая `Service Registry` для микросервисной архитектуры. Сервис выполняет следующие функции:
* Регистрация всех микросервисов в системе
* `Heartbeat`-проверка доступности (каждые 30 секунд (dev) / Каждый 60 секунд (prod))
* Удаление "мертвых" экземпляров (eviction)
* `Service Discovery` — предоставление информации о расположении сервисов
# 2. Технические компоненты
|Компонент|Назначение|
|-------------|-------------|
|`Spring Boot`|Базовый фреймворк|
|`Spring Cloud Netflix Eureka`|Service регистрации|
|`Spring Security`|Basic Authentication|
|`Spring Boot Actuator`|Мониторинг и метрики|
|`Maven`|Сборка проекта|
|`Docker`|Контейнеризация|
|`Java`|Платформа|
# 3. Конфигурация
## 3.1 Профили окружения
|Профиль|Файл|Назначение|
|-------------|-------------|-------------|
|dev|`application-dev.properties`|Локальная разработка|
|prod|`application-prod.properties`|Production окружение|
## 4.2 Параметры конфигурации
|Профиль|Значение|Назначение|
|-------------|-------------|-------------|
|`server-port`|`8761`|HTTP порт сервера|
|`spring.application.name`|`discovery-service`|Имя сервиса в реестре|
|`eureka.client.register-with-eureka`|`false`|Сервер не регистрирует себя|
|`eureka.client.fetch-registry`|`false`|Не загружает реестр с себя|
|`eureka.server.enable-self-preservation`|`false (dev)/true (prod)`|Защита от сетевых сбоекв|
|`eureka.server.eviction-interval-timer-in-ms`|`30 сек (dev)/60 сек (prod)`|Интервал проверки мертвых сервисов|
|`management.endpoints.web.exposure.include`|`health,info,metrics`|Доступные endpoints|
|`management.endpoint.health.show-details`|`always (dev) / when-authorized (prod)`|Детализация health| 
## 4.3 Логирование
|Параметр|Dev|Prod|
|-------------|-------------|-------------|
|Уровень (Eureka)|`DEBUG`|`WARN`|
|Уровень (Discovery)|`DEBUG`|`WARN`|
|Файл логов|❌|✅|
|-------------|-------------|-------------|
## 4.4 Security
|Параметр|Dev|Prod|
|-------------|-------------|-------------|
|`spring.security.user.name`|Название пользователя|
|`spring.security.user.password`|Пороль пользователя|
# 5. Api Endpoints
## 5.1 Eureka Endpoints
|Метод|Endpoint|Описание|
|-------------|-------------|-------------|
|`GET`|`/`|Web UI|
|`POST`|`/eureka/apps/{serviceId}`|Регистрация сервиса|
|`PUT`|`/eureka/apps/{serviceId}/{instanceId}/status`|Обновление статуса (heartbeat)|
|`DELETE`|`/eureka/apps/{serviceId}/{instanceId}`|Отмена регистрации|
|`GET`|`/eureka/apps`|Список всех сервисов (XML формат)|
|`GET`|`/eureka/apps/{serviceId}`|Информация о конкретном сервисе|
## 5.2 Actuator Endpoints
|Метод|Endpoint|Описание|
|-------------|-------------|-------------|
|`GET`|`/actuator/health`|Статус приложения (UP/DOWN), информация о компонентах (Eureka)|
|`GET`|`/actuator/info`|Информация о приложении (версия, имя, описание)|
|`GET`|`/actuator/metrics`|Список всех доступных метрик (`JVM`, процессор, память, HTTP запросы)|
# 6. Безопастность
## 6.1 Механизм аутентификации
* Тип HTTP Basic Authentication
* CSRF: Отключен (требование Eureka)

