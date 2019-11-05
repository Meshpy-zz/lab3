**Aplikacja generująca kalendarz w formacie iCal (.ics).**

| Metoda | Scieżka | Parametr | Opis |
| ------ | ------ | ------ | ------ |
| GET    | /calendar | month  | usługa zwraca plik w formacie .ics |

Przykładowe użycie usługi
```sh 
curl http://localhost:8080/calendar/11
```