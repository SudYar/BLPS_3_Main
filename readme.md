3 этап лабораторной работы по БЛПС

Реализует основной бизнес-процесс с [BPMN-cхемы](./BPMN_3.svg)

[Отчет](./Сударушкин_Ярослав_Лаб3.pdf), в котором описано, что изменилось по сравнению с 2 лабораторной, а также о
пользователях.

Кратко, что было в предыдущие этапы:

1. Описать бизнес-процесс в соответствии с нотацией BPMN 2.0, после чего
   реализовать его в виде приложения на базе Spring Boot.
   Сайт: https://www.forumhouse.ru/

> Реализован бизнес-процесс биржи, когда заказчик оформляет заказ и исполнитель выбирает и берется за него.

2. Доработать приложение из лабораторной работы #1, реализовав в нём управление
   транзакциями и разграничение доступа к операциям бизнес-логики в соответствии с
   заданной политикой доступа.

> Из логики процесса взяты роли заказчика и исполнителя

3. Доработать приложение из лабораторной работы #2, реализовав в нём асинхронное
   выполнение задач с распределением бизнес-логики между несколькими вычислительными
   узлами и выполнением периодических операций с использованием планировщика задач.

> Бизнес-процесс обсуждения заказа между заказчиком и исполнителем отделен в отдельное приложение, во время рефакторинга
> не планируется затрагивать второе приложение (а зачем?)

### РБДиП 1 Этап рефакторинга

1. Поправить checkstyle во всем проекте

### РБДиП 2 Этап рефакторинга

1. Исправить зависимости
2. Заменить простые методы на Lombok аннотации (где это ещё не сделано)