         В приложении есть 2 экрана.
         На первом экране присутствуют:
         - Кнопка для сохранения logcat в файл (вверху справа).
         - Картинка (любая, например картошка, но область в которой находится картинка, должна занимать 3/5
        высоты экрана). Для картинки опционально можно сделать любую анимацию.
         - Под картинкой горизонтально прокручиваемый двухстрочный список. Каждый элемент списка - id поста.
          Под id в одну строчку (можно сокращённо) title поста. В каждый момент времени должно отображаться
         не более 6 элементов (2 ряда по 3 элемента).
        - Под прокручиваемым списком должен отображаться точечный индикатор, отображающий текущую позицию списка.
         - При нажатии на любой элемент списка происходит переход на второй экран.
         Список постов приложение должно получать по адресу: http://jsonplaceholder.typicode.com/posts
         На втором экране:
        - В самом верху (в тулбаре) отображается строка "contact #X", где X - id контакта.
         - В верхней левой части отображается id поста, на который было нажато на предыдущий экран.
         - Справа от id поста располагаются имя и никнейм юзера (2 строки).
         - Ниже id поста отображается емейл, веб сайт, номер телефона и город пользователя.
        - Опционально - по нажатию на емейл, веб-сайт и номер телефона должны открыться соответствующие
        приложения на телефоне. При нажатии на город должна открыть карта с текущей координатой, равной той,
         что указана у данного юзера в профайле.
        - В самом низу с левой стороны есть кнопка сохранения инф-ции о юзере в базу данных.
        Детали пользователя приложение получает по адресу: http://jsonplaceholder.typicode.com/users/X , где X - целочисленный Id.
        Photo by Charles Deluvio on Unsplash
