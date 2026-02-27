# Лабораторные работы — 3-й семестр

Курс объектно-ориентированного программирования на Java.

**Язык:** Java 17+ (бэкенд), JavaScript / TypeScript (фронтенд, Lab7)  
**Сборка:** Maven (Labs 2–7 back), npm (Lab7 front)  
**БД:** PostgreSQL  

---

## Структура репозитория

```
├── Lab1/   — введение в Java: компиляция, классы, пакеты, JAR
├── Lab2/   — табулированные функции: ArrayTabulatedFunction, LinkedListTabulatedFunction
├── Lab3/   — ввод/вывод, многопоточность, дифференцирование, REST API (Spring Boot)
├── Lab6/   — расширенная версия Lab3: доработка API, JPA, Spring Security
└── Lab7/   — финальная: бэкенд (Spring Boot) + фронтенд (Next.js), интегрирование
    ├── back/
    └── front/
```

---

## Lab1 — Введение в Java

Знакомство с инструментарием языка. Четыре задания в папках `Task1`–`Task4` плюс `Task5` с JAR.

---

## Lab2 — Табулированные функции

Реализация библиотеки для хранения и интерполяции табулированных функций y = f(x).

**Тесты:** JUnit 5, покрытие всех публичных методов каждого класса.

**Сборка и запуск тестов:**
```bash
cd Lab2
mvn test
```

---

## Lab3 — Ввод/вывод, многопоточность, REST API

Расширение библиотеки из Lab2. Добавлены три независимых слоя функциональности.

### Ввод/вывод (`io`)

Чтение и запись табулированных функций в нескольких форматах.
Артефакты сохраняются в `input/` и `output/`.

### Многопоточность (`concurrent`)
Задачи чтения и записи. Потокобезопасная обёртка с блокировкой.

### Операции (`operations`)
Численное дифференцирование табулированных функций.

### REST API (Spring Boot)

Стек: Spring Boot · Spring Data JPA · Spring Security · PostgreSQL · Springdoc OpenAPI (Swagger UI)

**Эндпоинты:**

| Контроллер | Префикс | Назначение |
|---|---|---|
| `UserController` | `/user` | Регистрация, аутентификация |
| `MathFunctionController` | `/function` | CRUD математических функций |
| `LogController` | `/log` | Журнал операций |

**Сущности JPA:** `User`, `MathRes`, `Log`

**Конфигурация БД** (`application.properties`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=<your_password>
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```

**Swagger UI:** `http://localhost:8080/swagger-ui.html`

**Сборка и запуск:**
```bash
cd Lab3
mvn spring-boot:run
```

**Postman-коллекции** для тестирования API находятся в `src/test/resources/postman/`.

---

## Lab6 — Доработка REST API

Структурно идентична Lab3. Итерационная доработка: расширение покрытия тестами, уточнение обработки исключений (`GlobalExceptionHandler`), доработка JPA-репозиториев и сервисов.

**Сборка:**
```bash
cd Lab6
mvn spring-boot:run
```

---

## Lab7 — Финальная: полнофункциональное приложение

Итоговая работа. Объединяет все предыдущие компоненты и добавляет фронтенд.

### Бэкенд (`back/`)

Все возможности Lab6 плюс:

- **Новые функции:** `SinFunction`, `CosFunction`, `ExpFunction`
- **XML-сериализация:** `ArrayTabulatedFunctionSerializationXML` (Jackson XML)
- **Численное интегрирование:** `IntegralTask`, `IntegralTaskExecutor`
- **Новая JPA-сущность:** `CompositeFunctionEntity` — хранение составных функций в БД
- **UI-контроллеры** (`ui/controllers`): `TabulatedFunctionController`, `TabulatedFactoryController`, `CompositeFunctionController`, `TabulatedOperandsController`
- **Сервисы:** `SimpleFunctionService`, `CompositeFunctionService`, `SerializationService`, `StorageService`, `TempStorage`

**Сборка:**
```bash
cd Lab7/back
mvn spring-boot:run
```

### Фронтенд (`front/app/`)

Стек: Next.js 15 · React 18 · TypeScript · Tailwind CSS · DaisyUI · Chart.js · Axios

**Ключевые компоненты:**

| Компонент | Назначение |
|---|---|
| `LoginPage`, `RegisterModal` | Аутентификация |
| `CreateFunctionModal` | Создание табулированной функции |
| `ComplexFunctionModal` | Создание составной функции |
| `GraphModal` | Визуализация функции (Chart.js) |
| `DeriveModal` | Дифференцирование |
| `IntegralModal` | Интегрирование |
| `FunctionOperationsModal` | Арифметические операции над функциями |
| `SettingsModal` | Настройки фабрики (Array / LinkedList) |

Запросы к бэкенду проксируются через `/src/pages/api/proxy/[...path].js`.

**Установка и запуск:**
```bash
cd Lab7/front/app
npm install
npm run dev        # http://localhost:3000
```

**Переменные окружения** (`env.env`):
```
NEXT_PUBLIC_API_URL=http://localhost:8080
```

---

## Зависимости

| Инструмент | Версия |
|---|---|
| Java | 17+ |
| Maven | 3.8+ |
| Node.js | 18+ |
| PostgreSQL | 14+ |

---

## Тесты

Во всех лабах (Lab2–Lab7/back) тесты запускаются командой:

```bash
mvn test
```

Используемые библиотеки: JUnit 5, Mockito. Postman-коллекции для интеграционного тестирования API расположены в `src/test/resources/postman/` каждого модуля.

Спасибо, университету, что отбил желание взаимодействовать с Java.
