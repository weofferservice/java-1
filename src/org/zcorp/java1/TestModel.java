package org.zcorp.java1;

import org.zcorp.java1.model.*;

import java.util.GregorianCalendar;

public class TestModel {
    public static void main(String[] args) {
        Resume resume = new Resume("Профессор Фортран");

        /*
         * Контакты
         */
        Contact phone = new Contact(ContactType.PHONE, "+7 (985) 082-41-68");
        Contact skype = new Contact(ContactType.SKYPE, "prof.fortran");
        skype.setUrl("skype:prof.fortran");
        Contact email = new Contact(ContactType.EMAIL, "fortran.professor@gmail.com");
        email.setUrl("mailto:fortran.professor@gmail.com");
        Contact linkedin = new Contact(ContactType.LINKEDIN, "Профиль LinkedIn");
        linkedin.setUrl("https://www.linkedin.com/in/prof-fortran/");
        Contact github = new Contact(ContactType.GITHUB, "Профиль GitHub");
        github.setUrl("https://github.com/professor-fortran");
        Contact stackoverflow = new Contact(ContactType.STACKOVERFLOW, "Профиль Stackoverflow");
        stackoverflow.setUrl("https://stackoverflow.com/users/10036132/prof-fortran?tab=profile");
        Contact site = new Contact(ContactType.SITE, "Домашняя страница");
        site.setUrl("https://fortran.prof/");
        resume.setContacts(phone, skype, email, linkedin, github, stackoverflow, site);

        /*
         * Секции
         */
        // Позиция
        TextSection objective = new TextSection(SectionType.OBJECTIVE, "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.setSection(objective);

        // Личные качества
        TextSection personal = new TextSection(SectionType.PERSONAL, "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.setSection(personal);

        // Достижения
        ListSection achievement = new ListSection(SectionType.ACHIEVEMENT);
        achievement.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.setSection(achievement);

        // Квалификация
        ListSection qualifications = new ListSection(SectionType.QUALIFICATIONS);
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)");
        qualifications.add("Python: Django");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualifications.add("Администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        resume.setSection(qualifications);

        // Опыт работы
        JobDescription profFortran = new JobDescription(
                "Автор проекта",
                new GregorianCalendar(2013, 9, 1),
                null,
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        Organization profFortranOrg = new Organization("Школа профессора Фортрана", profFortran);
        profFortranOrg.setUrl("https://fortran.prof/");

        JobDescription wrike = new JobDescription(
                "Старший разработчик (backend)",
                new GregorianCalendar(2014, 9, 1),
                new GregorianCalendar(2016, 0, 1),
                "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        Organization wrikeOrg = new Organization("Wrike", wrike);
        wrikeOrg.setUrl("https://www.wrike.com/");

        JobDescription ritCenter = new JobDescription(
                "Java архитектор",
                new GregorianCalendar(2012, 3, 1),
                new GregorianCalendar(2014, 9, 1),
                "Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        Organization ritCenterOrg = new Organization("RIT Center", ritCenter);

        JobDescription luxoftDeutscheBank = new JobDescription(
                "Ведущий программист",
                new GregorianCalendar(2010, 11, 1),
                new GregorianCalendar(2012, 3, 1),
                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        Organization luxoftDeutscheBankOrg = new Organization("Luxoft (Deutsche Bank)", luxoftDeutscheBank);
        luxoftDeutscheBankOrg.setUrl("http://www.luxoft.ru/");

        JobDescription yota = new JobDescription(
                "Ведущий специалист",
                new GregorianCalendar(2008, 5, 1),
                new GregorianCalendar(2010, 11, 1),
                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        Organization yotaOrg = new Organization("Yota", yota);
        yotaOrg.setUrl("https://www.yota.ru/");

        JobDescription enkata = new JobDescription(
                "Разработчик ПО",
                new GregorianCalendar(2007, 2, 1),
                new GregorianCalendar(2008, 5, 1),
                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        Organization enkataOrg = new Organization("Enkata", enkata);
        enkataOrg.setUrl("http://enkata.com/");

        JobDescription siemensAG = new JobDescription(
                "Разработчик ПО",
                new GregorianCalendar(2005, 0, 1),
                new GregorianCalendar(2007, 1, 1),
                "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        Organization siemensAGOrg = new Organization("Siemens AG", siemensAG);
        siemensAGOrg.setUrl("https://www.siemens.com/ru/ru/home.html");

        JobDescription alcatel = new JobDescription(
                "Инженер по аппаратному и программному тестированию",
                new GregorianCalendar(1997, 8, 1),
                new GregorianCalendar(2005, 0, 1),
                "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        Organization alcatelOrg = new Organization("Alcatel", alcatel);
        alcatelOrg.setUrl("http://www.alcatel.ru/");

        OrganizationSection experience = new OrganizationSection(
                SectionType.EXPERIENCE,
                profFortranOrg,
                wrikeOrg,
                ritCenterOrg,
                luxoftDeutscheBankOrg,
                yotaOrg,
                enkataOrg,
                siemensAGOrg,
                alcatelOrg);
        resume.setSection(experience);

        // Образование
        JobDescription coursera = new JobDescription(
                "\"Functional Programming Principles in Scala\" by Martin Odersky",
                new GregorianCalendar(2013, 2, 1),
                new GregorianCalendar(2013, 4, 1));
        Organization courseraOrg = new Organization("Coursera", coursera);
        courseraOrg.setUrl("https://www.coursera.org/course/progfun");

        JobDescription luxoft = new JobDescription(
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                new GregorianCalendar(2011, 2, 1),
                new GregorianCalendar(2011, 3, 1));
        Organization luxoftOrg = new Organization("Luxoft", luxoft);
        luxoftOrg.setUrl("http://www.luxoft-training.ru/training/catalog/course.html?ID=22366");

        JobDescription siemensAGSchool = new JobDescription(
                "3 месяца обучения мобильным IN сетям (Берлин)",
                new GregorianCalendar(2005, 0, 1),
                new GregorianCalendar(2005, 3, 1));
        Organization siemensAGSchoolOrg = new Organization("Siemens AG", siemensAGSchool);
        siemensAGSchoolOrg.setUrl("http://www.siemens.ru/");

        JobDescription alcatelSchool = new JobDescription(
                "6 месяцев обучения цифровым телефонным сетям (Москва)",
                new GregorianCalendar(1997, 8, 1),
                new GregorianCalendar(1998, 2, 1));
        Organization alcatelSchoolOrg = new Organization("Alcatel", alcatelSchool);
        alcatelSchoolOrg.setUrl("http://www.alcatel.ru/");

        JobDescription spbSchool1 = new JobDescription(
                "Аспирантура (программист С, С++)",
                new GregorianCalendar(1993, 8, 1),
                new GregorianCalendar(1996, 6, 1));
        JobDescription spbSchool2 = new JobDescription(
                "Инженер (программист Fortran, C)",
                new GregorianCalendar(1987, 8, 1),
                new GregorianCalendar(1993, 6, 1));
        Organization spbSchoolOrg = new Organization(
                "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики",
                spbSchool1,
                spbSchool2);
        spbSchoolOrg.setUrl("http://www.ifmo.ru/");

        JobDescription mipt = new JobDescription(
                "Закончил с отличием",
                new GregorianCalendar(1984, 8, 1),
                new GregorianCalendar(1987, 5, 1));
        Organization miptOrg = new Organization("Заочная физико-техническая школа при МФТИ", mipt);
        miptOrg.setUrl("http://www.school.mipt.ru/");

        OrganizationSection education = new OrganizationSection(SectionType.EDUCATION);
        education.add(courseraOrg);
        education.add(luxoftOrg);
        education.add(siemensAGSchoolOrg);
        education.add(alcatelSchoolOrg);
        education.add(spbSchoolOrg);
        education.add(miptOrg);
        resume.setSection(education);

        System.out.println(resume);
    }
}
