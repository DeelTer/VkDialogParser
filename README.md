# VkDialogParser
Парсит файлы сообщений, полученные из [архива вк](https://vk.com/data_protection?section=rules&scroll_to_archive=1)

## Как пользоваться
Этот модуль предназначен для ядра Milkshake, которое находится в закрытом репозитории. Поэтому Вы можете лишь скопировать код и зависимости проекта частями
```java
VkParseManager.getFilesInFolder(); // Возвращает лист файлов в папке модуля
VkParseManager.getMessagesFromFiles(List<File> htmlFiles); // Возвращает лист объектов VkMessage. Важно, чтобы все передаваемые файлы были формата .html
VkParseManager.getMessagesFromFile(File htmlFile); // То же самое, но с одиночным файлом 
```
