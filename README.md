# java-1
Java-проект для портфолио - 1

# Стек технологий

Web-слой:
- [Servlet API](https://docs.oracle.com/cd/E17802_01/products/products/servlet/2.5/docs/servlet-2_5-mr2/)
- [JSP](https://ru.wikipedia.org/wiki/JavaServer_Pages)
- [JSTL](https://ru.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library)

Хранение данных:
- в памяти на основе массива
- в памяти в виде [отсортированного массива](https://www.geeksforgeeks.org/arrays-binarysearch-java-examples-set-1/)
- в памяти в виде [списка](https://docs.oracle.com/javase/8/docs/api/java/util/List.html)
- в памяти в виде хеш-массива [Map](https://docs.oracle.com/javase/8/docs/api/java/util/Map.html)
- в файловой системе при помощи [File API](https://docs.oracle.com/javase/7/docs/api/java/io/File.html)
- в файловой системе при помощи [Java 7 NIO File API](https://www.baeldung.com/java-nio-2-file-api)
- в файловой системе при помощи [сериализации Java](https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html)
- в файловой системе в формате JSON [Google GSON](https://github.com/google/gson)
- в файловой системе в формате XML [JAXB](https://www.oracle.com/technetwork/articles/javase/index-140168.html)
- в реляционной БД [PostgreSQL](https://www.postgresql.org/)

Деплой приложения:
- в контейнер сервлетов [Tomcat](http://tomcat.apache.org/)

Тестирование:
- модульные тесты [JUnit](https://junit.org/junit4/)

Логирование:
- java.util.Logging [JUL](https://docs.oracle.com/javase/7/docs/api/java/util/logging/package-summary.html)

# Общее описание

Приложение создано с целью показать навыки работы с различными способами хранения данных, которые перечислены в списке выше.
Показано адекватное применение различных паттернов проектирования, таких как:
- шаблонный метод
- стратегия
- синглтон
- итератор
- декоратор
- фабричный метод
- адаптер
