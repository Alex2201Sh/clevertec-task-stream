Задача №16 (собственная) -
В списке клиентов (Customer), содержащих адрес (CustomerAddress)
найти клиентов, количество которых в городе 3 или больше;
отсортировать по городу, потом по улице,
потом по номеру дома в обратном порядке,
затем по фамилии.
Полученный список вывести в консоль в формате:
Фамилия Имя, город, улица, дом - квартира.
Пример вывода одной строки:
"Ellsworth Mills, city: Columbus, street: Borge, 185-72"

Исходные данные в папке "resources" в файле "customers.json"

Для решения задачи созданы два класса в моделях:

Customer с полями
* int id;
* String firstName;
* String lastName;
* String email;
* CustomerAddress address;

CustomerAddress с полями
* int zip;
* String city;
* String street;
* String buildingNumber;
* String flatNumber;