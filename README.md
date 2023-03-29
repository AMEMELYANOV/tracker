# Tracker

## <p id="contents">Оглавление</p>

<ul>
<li><a href="#01">Описание проекта</a></li>
<li><a href="#02">Стек технологий</a></li>
<li><a href="#03">Требования к окружению</a></li>
<li><a href="#04">Сборка и запуск проекта</a>
    <ol type="1">
        <li><a href="#0401">Сборка проекта</a></li>
        <li><a href="#0402">Запуск проекта</a></li>
    </ol>
</li>
<li><a href="#05">Взаимодействие с приложением</a>
    <ol  type="1">
        <li><a href="#0501">Создание заявки</a></li>
        <li><a href="#0502">Просмотр списка заявок</a></li>
        <li><a href="#0503">Редактирование заявки</a></li>
        <li><a href="#0504">Удаление заявки</a></li>
        <li><a href="#0505">Поиск заявки по id</a></li>
        <li><a href="#0506">Поиск заявки по наименованию</a></li>
        <li><a href="#0507">Выход из приложения</a></li>
    </ol>
</li>
<li><a href="#contacts">Контакты</a></li>
</ul>

## <p id="01">Описание проекта</p>

Консольное приложение для хранения заявок.
Возможна работа с СУБД PosgreSQL для хранения заявок, как с помощью
JDBC, так и с использованием Hibernate.

Функционал:

* Создание заявок.
* Просмотр всего списка заявок.
* Редактирование заявки.
* Удаление заявки.
* Поиск заявки по id.
* Поиск заявки по имени.

<p><a href="#contents">К оглавлению</a></p>

## <p id="02">Стек технологий</p>

- Java 17
- JUnit 4
- PostgreSQL 14, Hibernate 5.4
- Maven 3.8

  Инструменты:

- Javadoc, JaCoCo, Checkstyle

<p><a href="#contents">К оглавлению</a></p>

## <p id="03">Требования к окружению</p>

Java 17, Maven 3.8, PostgreSQL 14 

<p><a href="#contents">К оглавлению</a></p>

## <p id="04">Сборка и запуск проекта</p>

### <p id="0401">1. Сборка проекта</p>

Команда для сборки в jar:

`mvn clean package -DskipTests`

<p><a href="#contents">К оглавлению</a></p>

### <p id="0402">2. Запуск проекта</p>

Перед запуском проекта необходимо создать базу данных todo
в PostgreSQL, команда для создания базы данных:

`create database tracker;`

Средство миграции Liquibase автоматически создаст структуру
базы данных и наполнит ее предустановленными данными.
Команда для запуска приложения:

`java -jar target/tracker.jar`

<p><a href="#contents">К оглавлению</a></p>

## <p id="05">Взаимодействие с приложением</p>

### <p id="0501">1. Создание заявки</p>

![alt text](images/tracker_img_1.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0502">2. Просмотр списка заявок</p>

![alt text](images/tracker_img_2.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0503">3. Редактирование заявки</p>
При редактировании, если id заявки указан неверно, будет выведено сообщение
о невозможности отредактировать заявку.

![alt text](images/tracker_img_3_1.jpg)

Если заявка существует, она будет сохранена с новыми значениями
полей name и description.

![alt text](images/tracker_img_3_2.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0504">4. Удаление заявки</p>

Удаление заявки производится по id, если заявка с указанным 
id не найдена, выводится сообщение о невозможности удаления.

![alt text](images/tracker_img_4_1.jpg)

Если найдена, производится удаление заявки.

![alt text](images/tracker_img_4_2.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0505">5. Поиск заявки по id</p>

Если заявка с указанным id не найдена, будет выведено сообщение об отсутствии 
заявки с указанным id.

![alt text](images/tracker_img_5_1.jpg)

Если заявка существует, в консоль будет выведено её содержание.

![alt text](images/tracker_img_5_2.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0506">6. Поиск заявки по наименованию</p>

Если заявка с указанным name не найдена, будет выведено сообщение об отсутствии
заявки с указанным name.

![alt text](images/tracker_img_6_1.jpg)

Если заявка существует, будет выведено её содержание.

![alt text](images/tracker_img_6_2.jpg)

<p><a href="#contents">К оглавлению</a></p>

### <p id="0507">7. Выход из приложения</p>

![alt text](images/tracker_img_7.jpg)

<p><a href="#contents">К оглавлению</a></p>

## <p id="contacts">Контакты</p>

[![alt-text](https://img.shields.io/badge/-telegram-grey?style=flat&logo=telegram&logoColor=white)](https://t.me/T_AlexME)&nbsp;&nbsp;
[![alt-text](https://img.shields.io/badge/@%20email-005FED?style=flat&logo=mail&logoColor=white)](mailto:amemelyanov@yandex.ru)&nbsp;&nbsp;

<p><a href="#contents">К оглавлению</a></p>