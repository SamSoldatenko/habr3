# habr3

[![Build Status](https://travis-ci.org/SamSoldatenko/habr3.svg?branch=master)](https://travis-ci.org/SamSoldatenko/habr3)

Это Android приложение - демонстрация применения MVC для обновления UI по изменениям
сделаным в асинхронных операциях. Подход описан в статье https://habrahabr.ru/post/281290/

## Функциональность

Электронный билет для электронной очереди.

1. Пользователь входи в банк, нажимает кнопку "Взять билет" в приложении.
2. Приложение запрашивает билет с сервера (эмулируется)
3. После получения билета приложение отображает билет

## Технологии/библиотеки

1. Android Databinding
2. Android Suport Library
3. Roboguice
4. Mockito
5. Powermock

