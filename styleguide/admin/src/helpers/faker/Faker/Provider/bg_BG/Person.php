<?php

namespace Faker\Provider\bg_BG;

class Person extends \Faker\Provider\Person
{
    protected static $lastNameFormat = array(
        '{{lastNameMale}}',
        '{{lastNameFemale}}',
    );
    
    protected static $maleNameFormats = array(
        '{{firstNameMale}} {{lastNameMale}}',
        '{{firstNameMale}} {{lastNameMale}}',
        '{{firstNameMale}} {{lastNameMale}}',
        '{{titleMale}} {{firstNameMale}} {{lastNameMale}}',
    );

    protected static $femaleNameFormats = array(
        '{{firstNameFemale}} {{lastNameFemale}}',
        '{{firstNameFemale}} {{lastNameFemale}}',
        '{{firstNameFemale}} {{lastNameFemale}}',
        '{{titleFemale}} {{firstNameFemale}} {{lastNameFemale}}'
    );

    protected static $firstNameMale = array(
        'Абен', 'Аблен', 'Август', 'Августиан', 'Августин', 'Авел', 'Авер', 'Аверно', 'Авксентий', 'Аво', 'Авраам', 'Аврам', 'Аврели', 'Аврелий', 'Аврор', 'Агапи', 'Агапий', 'Агатопод', 'Агент', 'Аглай', 'Аглар', 'Агнен', 'Агнеш', 'Агоп', 'Агъци', 'Адалберт', 'Адам', 'Адеан', 'Аделин', 'Адем', 'Аднан', 'Адриан', 'Адриян', 'Аерозол', 'Азалия', 'Айдемир', 'Акашия', 'Аксакусти', 'Аксидан', 'Аксинтия', 'Албен', 'Алберт', 'Албияна', 'Алдин', 'Алевандър', 'Алег', 'Алек', 'Алекзандриян',
        'Беримир', 'Берин', 'Берислав', 'Берия', 'Беро', 'Берослав', 'Бетина', 'Бетино', 'Бечо', 'Билян', 'Бинко', 'Биньо', 'Биню', 'Бисенти', 'Бисер', 'Благо', 'Благовест', 'Благой', 'Благомир', 'Благосвет', 'Блаже', 'Бог', 'Богдан', 'Богиня', 'Богой', 'Боголюб', 'Богомил', 'Богослав', 'Бодромир', 'Божан', 'Божидар', 'Божик', 'Божил', 'Божимир', 'Божин', 'Божинел', 'Божко', 'Божо', 'Божур', 'Боил', 'Боила', 'Бойко', 'Бойо', 'Бойчо', 'Болен', 'Болеслав', 'Боне', 'Бонислав', 'Бонко', 'Боно',
        'Веселин', 'Весислав', 'Веско', 'Весо', 'Веспасиян', 'Ветко', 'Вечко', 'Вигалот', 'Виго', 'Виделин', 'Виден', 'Видин', 'Видол', 'Видослав', 'Видю', 'Викенти', 'Виктор', 'Вилиан', 'Вилизар', 'Вилизара', 'Вилислав', 'Вилиян', 'Вилям', 'Винету', 'Винко', 'Вино', 'Винсънт', 'Винченцо', 'Виолет', 'Виолин', 'Висарион', 'Виталий', 'Витан', 'Витко', 'Витлян', 'Витомир', 'Витош', 'Вихрен', 'Вихрони', 'Вихър', 'Вичо', 'Виша', 'Вишан', 'Вишетин', 'Вишню', 'Влад', 'Владилен', 'Владимер', 'Владимир',
        'Галентин', 'Галиен', 'Галимир', 'Галин', 'Галиян', 'Гани', 'Ганислав', 'Ганцомир', 'Ганчо', 'Ганьо', 'Гаро', 'Гатьо', 'Гацо', 'Гачо', 'Гвардиана', 'Гелемир', 'Генади', 'Генади Валериев', 'Генадий', 'Генислав', 'Генко', 'Гено', 'Генчо', 'Геньо', 'Гео', 'Геодим', 'Геомил', 'Георги', 'Герасим', 'Герган', 'Гергей', 'Гергелюб', 'Гергин', 'Герго', 'Гердан', 'Герман', 'Геро', 'Герой', 'Герчо', 'Гетислав', 'Гетко', 'Гето', 'Гецо', 'Гечко', 'Гечо', 'Гешо', 'Гивеза', 'Гиздален', 'Гико', 'Гилдрой',
        'Делчо', 'Делян', 'Деляна', 'Демин', 'Демир', 'Демян', 'Дениз', 'Деника', 'Денимир', 'Денис', 'Денислав', 'Дениян', 'Денчо', 'Деньо', 'Дердидас', 'десилиан', 'Десимир', 'Десислав', 'Деслав', 'Деспин', 'Деспинка', 'Деспот', 'Детелин', 'Детелюб', 'Дечко', 'Дечо', 'Дечю', 'Дешо', 'Деян', 'Джанер', 'Джанко', 'Джихад', 'Джон-стефан', 'Джулиен', 'Диаманди', 'Диамантина', 'Диан', 'Диван (на дядо Диан и дядо Иван)', 'Дивизие', 'Дивизия', 'Дивил', 'Дидко', 'Диего', 'Дийвид', 'Дико', 'Дилян',
        'Евстати', 'Евстатий', 'Евстахий', 'Евтим', 'Егор', 'Едвин', 'Едит', 'Еднорог', 'Едрю', 'Едуард', 'Еким', 'Ектар', 'Ектор', 'Елвис', 'Елеан', 'Електрон', 'Елемаг', 'Елен', 'Еленко', 'Елиан', 'Елиас', 'Елиезер', 'Елизабет', 'Елин', 'Елисей', 'Елисия', 'Елко', 'Елтимир', 'Ельо', 'Ема-Бела', 'Еманоил', 'Емануел', 'Емануил', 'Емил', 'Емилиан', 'Емилиян', 'Ендо', 'Енис', 'Енчо', 'Еньо', 'Еню', 'Ервин', 'Ередин', 'Еремия', 'Ерик', 'Ерина', 'Ерол', 'Ерослав', 'Ерсен', 'Есен', 'Етиен', 'Ефка',
        'Заварин', 'Завен', 'Замфир', 'Занго', 'Занко', 'Заприн', 'Запрян', 'Зарко', 'Зафер', 'Зафир', 'Захар', 'Захари', 'Захарин', 'Захо', 'Звездан', 'Звезделюб', 'Звездин', 'Звездислав', 'Звездиян', 'Звездолет', 'Звездомир', 'Згура', 'Здравелин', 'Здравец', 'Здравко', 'Здравомир', 'Здравчо', 'Зенгин', 'Зика', 'Зинко', 'Зинови', 'Златан', 'Злати', 'Златил', 'Златимир', 'Златин', 'Златиян', 'Златко', 'Златогор', 'Златозар', 'Златомир', 'Златослав', 'Златоцвет', 'Златьо', 'Золтан', 'Зоран',
        'Илиомар', 'Илич', 'Илия', 'Илиян', 'Илко', 'Илчо', 'Имилиан', 'Ингемунд', 'Инко', 'Инокентий', 'Инчо', 'Иполит', 'Ириан', 'Ириней', 'иринеус', 'Ириян', 'Ирко', 'Ирма', 'Ирник', 'Исайа', 'Исак', 'Исидор', 'Искрен', 'Искър', 'Исперих', 'Истан', 'Истатко', 'Истилян', 'Исус', 'Итан', 'Итко', 'Ихтиандър', 'Ицо', 'Ичо', 'Йено', 'Йеремия', 'Йоан', 'Йоан-Александър', 'Йоан-иво', 'Йов', 'Йован', 'Йовица', 'Йовко', 'Йово', 'Йовро', 'Йовцо', 'Йовчо', 'Йожи', 'Йоил', 'Йоланд', 'Йолиян', 'Йолко',
        'Карен', 'Карим', 'Карин', 'Карло', 'Кармен', 'Каролин', 'Карчо', 'Касандър', 'Катакомб', 'Каталинка', 'Катерин', 'Кевин', 'Кеворк', 'Кери', 'Кибер', 'Кимба', 'Кимбо', 'Кимо', 'Кимон', 'Кимчо', 'Кин', 'Кинка', 'Кинта', 'Киприслав', 'Киприян', 'Кириен', 'Кирил', 'Кирко', 'Кирчо', 'Киряк', 'Киряки', 'Киряко', 'Кис', 'Кит', 'Кито', 'Китодар', 'Китомир', 'Клеантин', 'Клим', 'Климент', 'Кнут', 'Козма', 'Койно', 'Койо', 'Койчо', 'Коко', 'Коле', 'Колонина', 'Колчо', 'Кольо', 'Колю', 'Комнин',
        'Лалко', 'Лало', 'Лальо', 'Ламби', 'Ламбо', 'Ламбри', 'Ламбю', 'Ланселот', 'Ласкал', 'Ласкар', 'Ластър', 'Латин', 'Латко', 'Латьо', 'Латю', 'Лашко', 'ЛЕА-МАРИЯ', 'Леандър', 'Лев', 'Левент', 'Левчо', 'Леко', 'Ленин', 'Ленко', 'Леон', 'Леонардо', 'Леонид', 'Лесе', 'Лефтер', 'Лечо', 'Лилко', 'Лило', 'Лилчо', 'Лилян', 'Лимон', 'Лимончо', 'Липе', 'Лихия', 'Личо', 'Ловчо', 'Лозан', 'Лозана', 'Лозен', 'Лора-софия', 'Лоранс', 'Лоренцо', 'Лука', 'Лукан', 'Луко', 'Лули', 'Лулчо', 'Лусио', 'Лусия',
        'Маноел', 'Манол', 'Манолин', 'Маноло', 'Мантас', 'Мануил', 'Мануш', 'Манчестър Юнайтед', 'Манчо', 'Маньо', 'Маню', 'Марангони', 'Маргарин', 'Маргарит', 'Марек', 'Мариан', 'Марий', 'Марин', 'Маринел', 'Маринчо', 'Марио', 'Мариоллита', 'Мариочка', 'Маритна', 'Мариян', 'Марк', 'Марк-антоний', 'Марко', 'Маркус', 'Мартен', 'Мартин', 'Мартиниян', 'Марто', 'Маруш', 'Марчело', 'Маслина', 'Матей', 'Матьо', 'Матю', 'Махно', 'Машо', 'Медиан', 'Менко', 'Мено', 'Мерилин', 'Месак', 'Метакса',
        'Найо', 'Найчо', 'Наке', 'Нако', 'Нанко', 'Нанков', 'Нано', 'Нансимир', 'Нанчо', 'Наню', 'Нарцислав', 'Наско', 'Настимир', 'Настрадин', 'Натанаил', 'Нати', 'Натко', 'Наум', 'Нафисат', 'Нафтали', 'Нацко', 'Нацо', 'Начиян', 'Начко', 'Начо', 'Невелин', 'Невен', 'Невенко', 'Невилиян', 'Невян', 'Негослав', 'Неделин', 'Неделчо', 'Недислав', 'Недко', 'Недолюб', 'Недьо', 'Недю', 'Недялко', 'Нейко', 'Нейчо', 'Нелко', 'Нелчо', 'Нене', 'Ненко', 'Нено', 'Ненсислав', 'Ненчо', 'Неокли', 'Нерес',
        'Обретим', 'Ованес', 'Огин', 'Огнемир', 'Огнен', 'Огнян', 'Океан', 'Олег', 'Олек', 'Олимпи', 'Омар', 'Омуртаг', 'Оник', 'Онуфри', 'Ончо', 'Орел', 'Орлин', 'Ортодокси', 'Орфей', 'Орхидей', 'Орце', 'Оскар', 'Оханес', 'Павел', 'Павелин', 'Павил', 'Павко', 'Павлик', 'Павлин', 'Павлинчо', 'Павломир', 'Паисий', 'Пако', 'Палми', 'Палмиро', 'Панаври', 'Панай', 'Панайот', 'Панде', 'Панделис', 'Пане', 'Панкртийян', 'Пано', 'Панталей', 'Пантелей', 'Пантер', 'Панто', 'Пантю', 'Панчо', 'Паолина',
        'Параскев', 'Параход', 'Парашкев', 'Парашкеван', 'Паруш', 'Паскал', 'Паско', 'Паспанахил', 'Пато', 'Патрик', 'Патьо', 'Паулин', 'Паун', 'Пацо', 'Пачо', 'Пашо', 'Пейко', 'Пейо', 'Пейодон', 'Пейтан', 'Пейчин', 'Пейчо', 'Пеко', 'Пелай', 'Пеле', 'Пене', 'Пенко', 'Пенчин', 'Пенчо', 'Пеньо', 'Пеню', 'Пео', 'Пепино', 'Пепислав', 'Пепо', 'Пепонаки', 'Перикъл', 'Персиана', 'Персиян', 'Перчо', 'Петиконгрес', 'Петкан', 'Петко', 'Пето', 'Петраки', 'Петран', 'Петрана', 'Петринел', 'Петрозар', 'Петромил',
        'Рангел', 'Рангел-любими', 'Рандю', 'Ранчо', 'Расате', 'Ратослав', 'Рафаил', 'Рачко', 'Рачо', 'Рашко', 'Рашо', 'Раю', 'Раян', 'Реан', 'Рем', 'Рикардо', 'Риналдо', 'Рис', 'Ристя', 'Ричард', 'Ричерд', 'Роберт', 'Роберто', 'Робин', 'Робърт', 'Рогелин', 'Рогелина', 'Рогена', 'Родан', 'Родион', 'Розалин', 'Розин', 'Розоцвет', 'Ройо', 'Роксан', 'Роман', 'Ромел', 'Ромелина', 'Ромен', 'Ромео', 'Ромил', 'Ромул', 'Росен', 'Росенка', 'Росимир', 'Росицвет', 'Роска', 'Роско', 'Ростиана', 'Ростимир', 'Ростислав',
        'Светломир', 'Светлю', 'Светозар', 'Светослав', 'Свилен', 'Себастиан', 'Себахтин', 'Севан', 'Севар', 'Севастин', 'Севдалин', 'Севдан', 'Севелин', 'Северин', 'Седевчо', 'Седеф', 'Седефчо', 'Селен', 'Селена', 'Сенко', 'Серафим', 'Сергей', 'Сеслав', 'Сиви', 'Сивко', 'Сидер', 'Сидония', 'Сидор', 'Сиен', 'Силаги', 'Силан', 'Силвестър', 'Силвио', 'Силвиян', 'Силен', 'Силян', 'Симеон', 'Симо', 'Сирман', 'Сифоня', 'Скорбут', 'Слав', 'Славдо', 'Славе', 'Славей', 'Славейко', 'Славен', 'Слави', 'Славил',
        'Теодоси', 'Теодосий', 'Теодослав', 'Теодостин', 'Теофан', 'Теофил', 'Теохар', 'Тервел', 'Тигрони', 'Тило', 'Тильо', 'Тимо', 'Тимон', 'Тимотей', 'Тимчо', 'Тинко', 'Тино', 'Тинчо', 'Тихо', 'Тихол', 'Тихомир', 'Тихон', 'Тишо', 'Тоде', 'Тодомирка', 'Тодор', 'Тодораки', 'Тодорин', 'Тодорина', 'Токимир', 'Толек', 'Толю', 'Тома', 'Томас', 'Томен', 'Томи', 'Томинка', 'Томислав', 'Томо', 'Тоне', 'Тони', 'Тонимир', 'Тонислав', 'Тонко', 'Тончо', 'Тоньо', 'Топалко', 'Тополко', 'Тотко', 'Тотьо', 'Тотю', 'Тоцо',
        'Филатей', 'Фили', 'Филидан', 'Филимон', 'Филион', 'Филип', 'Филипас', 'Филипопол', 'Филко', 'Филомир', 'Филчо', 'Фильо', 'Финдо', 'Финдол', 'Фиро', 'Фирчо', 'Фичо', 'Флори', 'Флориан', 'Флорин', 'Флоро', 'Фори', 'Фосил', 'Фотин', 'Франк', 'Франц', 'Францислав', 'Фрацил', 'Фреди', 'Фродо', 'Фуго', 'Фуко', 'Фъстък', 'Фътьо', 'Фьодор', 'Хавтелин', 'Ханко', 'Хараламби', 'Харалампи', 'Харалан', 'Харбингър', 'Хари', 'Харизан', 'Харитии', 'Харитон', 'Хасан', 'Хасатин', 'Хачо', 'Хвойне', 'Хебър', 'Хектор',
        'Хераклит', 'Хернани', 'Херодот', 'Хефестион', 'Химинай', 'Хинко', 'Хино', 'Хитко', 'Хороз', 'Храбрин', 'Храбър', 'Хранимир', 'Хранко', 'Хрелко', 'Хрельо', 'Хрисим', 'Хрисимир', 'Хрисо', 'Христалин', 'Христивилин', 'Христиела', 'Христилиан', 'Христилин', 'християн', 'Христо', 'Христо-никола', 'Христодор', 'Христозар', 'Христозорнициан', 'Христозорницомил', 'Христомил', 'Христомир', 'Христоско', 'Христослав', 'Христофор', 'Хрисчо', 'Хрондел', 'Хрусан', 'Хубав', 'Хубавен', 'Хубан', 'Хубен',
        'Цоньо', 'Цоню', 'Цоцо', 'Цочо', 'Цъки', 'Чавдар', 'Чанкете', 'Чанко', 'Чано', 'Чаньо', 'Чаню', 'Чардафон', 'Чародей', 'Чауш', 'Чачо', 'Чвор', 'Чедомир', 'Ченко', 'Ченю', 'Чепо', 'Чернобил', 'Черноризец', 'Черньо', 'Чийо', 'Чико', 'Чило', 'Чонар', 'Чони', 'Чоно', 'Чоню', 'Чора', 'Чочо', 'Чочомир', 'Чубрик', 'Чуде', 'Чудо', 'Чудомир', 'Чудослав', 'Чук', 'Шабан', 'Шамо', 'Шанко', 'Шаноу', 'Шаро', 'Шейна', 'Шеки', 'Шенко', 'Шенол', 'Шибил', 'Шидер', 'Шильо', 'Шинко', 'Шино', 'Шипчан', 'Ширко', 'Шишман',
        'Шкодри', 'Шмильо', 'Шмулю', 'Шпилко', 'Шпиньо', 'Шушо', 'Щедрин', 'Щедю', 'Щеки', 'Щено', 'Щеню', 'Щерион', 'Щериян', 'Щерко', 'Щеро', 'Щерьо', 'Щерю', 'Щилиян', 'Щилян', 'Щирян', 'Щоно', 'Щтърбан', 'Щтъркан', 'Щурк', 'Щурчо', 'Щърбан', 'Щъркан', 'Ъглен', 'Ълен', 'Ърнест', 'Ъруин', 'Ърчо', 'Ьобирдар', 'Юги', 'Юли', 'Юлиан', 'Юлий', 'Юлиян', 'Юрдан', 'Юри', 'Юрий', 'Юстин', 'Юстиниан', 'Яблен', 'Явлен', 'Явор', 'Яго', 'Ягодин', 'Язо', 'Яким', 'Яко', 'Якоб', 'Яков', 'Якослав', 'Ямболен', 'Ян', 'Янадин',
        'Янаки', 'Янакин', 'Яначко', 'Яне', 'Янег', 'Янедин', 'Янек', 'Янеслав', 'Яни', 'Яниел', 'Яник', 'Янимир', 'Янис', 'Янислав', 'Яничко', 'Янко', 'Янкул', 'Янкуп', 'Яно', 'Яномил', 'Янтар', 'Януш', 'Янцислав', 'Янче', 'Янчо', 'Ярно', 'Яромир', 'Ярослав', 'Ярце', 'Ярчо', 'Яръм', 'Ярю', 'Ясен', 'Ясер', 'Ястреб', 'Ятан', 'Яцо', 'Ячко', 'Ячо', 'Яшар', 'Яшка', 'Яшо', 'Яшон'
    );

    protected static $firstNameFemale = array(
        'Авгия', 'Авигея', 'Авторка', 'Аглая', 'Аглоида', 'Агнешка', 'Адамина', 'Адра', 'Адрианиа', 'Аела', 'Айрен', 'Аксентия', 'Алания', 'Албина', 'Александрина', 'Алексиа', 'Аленка', 'Алиана', 'Алисия', 'Алтая', 'Амбър', 'Амория', 'Ана Мария', 'Анатолия', 'Ангелка', 'Андика', 'Андриана', 'Анелина', 'анета', 'Анза', 'Анимира', 'Аница', 'Аномалия', 'Антоалина', 'Антонела', 'Ануша', 'Анхея-мей', 'Аполинария', 'Аралия', 'Аркадия', 'Арсена', 'Аселина', 'Асифа', 'Астромерия', 'Атина', 'Аурора', 'Багра',
        'Балина', 'Бацислава', 'Беатрис', 'Бела', 'Белисима', 'Беломира', 'Бенелена', 'Берислава', 'Бея', 'Билена', 'Бисера', 'Биянка', 'Благодатка', 'Благосвета', 'Богдалина', 'Богородка', 'Бодурка', 'Божидара-силвестра', 'Божинела', 'Божурка', 'Бонифация', 'Борена', 'Бориска', 'Борянка', 'Боца', 'Бригита', 'Бронислава', 'Буна', 'Буча', 'Бързана', 'Ваклина', 'Валерия', 'Валя', 'Вангелия', 'Ванухи', 'Варта', 'Васенка', 'Василина', 'Васка', 'Вашиля', 'Вежда', 'Велиана', 'Велинна', 'Велиянка', 'Венда',
        'Венеция', 'Венислава', 'Венчислава', 'Верена', 'Верислава', 'Веса', 'Веселинка', 'Весна', 'Веца', 'Видима', 'Виктория', 'Вилия', 'Вилхема', 'Виолета', 'Виржиния', 'Витомира', 'Вишка', 'Владилена', 'Владлена', 'Водица', 'Войнка', 'Вула', 'Възкресения', 'Въльо', 'Върбунка', 'Въца', 'Габи', 'Галена', 'Галина', 'Галя', 'Гануца', 'Гвоздейка', 'Гена', 'Георгелена', 'Георгица', 'Герга', 'Гердана', 'Гертруда', 'Гиздана', 'Гичка', 'Гордана', 'Гория', 'Гоца', 'Графица', 'Грета', 'Гримяна', 'Гроздинка',
        'Гуна', 'Гъда', 'Гълъбица', 'Гюгра', 'Гюргя', 'Дакота', 'Дамяна', 'Данелина', 'Данимира', 'Данка', 'Дарданела', 'Дария', 'Дафинка', 'Деа', 'Деви', 'Делиана', 'Деля', 'Демирела', 'Дениандра', 'Дениславена', 'Денница', 'Десимиляна', 'Десияна', 'Дефлорина', 'Дея', 'Джана', 'Джиневра', 'Джулия', 'Диана - Мария', 'Дида', 'Дилмана', 'Димитра', 'Димка', 'Динна', 'Добрина', 'Дойка', 'Доменика', 'Доника', 'Дора-Анна', 'Дорина', 'Доста', 'Доча', 'Драгица', 'Дренка', 'Дуда', 'Душка', 'Дюкяна', 'Евангелина',
        'Евдокия', 'Евридика', 'Едита', 'Ел', 'Елдора', 'Еленица', 'Елеонета', 'Ели', 'Елиз', 'Елина', 'Елиса', 'Елица', 'Елма', 'Елфида', 'Емануила', 'Емма', 'Еница', 'Ергана', 'Ермиля', 'Естела', 'Ефимена', 'Ефросиния', 'Жаклин', 'Жанин', 'Жара', 'Жейна', 'Желязка', 'Женимира', 'Жива', 'Живомира', 'Жичка', 'Жорка', 'Жулиана', 'Заека', 'Занка', 'Зафа', 'Захаринка', 'Звездемира', 'Здравелина', 'Здухостина', 'Зинаида', 'Златея', 'Златка', 'Златомира', 'Зоичка', 'Зорка', 'Зузичка', 'Ивалена', 'ивамина',
        'Иванеса', 'Иваничка', 'Ивелиана', 'Ивинка', 'Иглика', 'Изидора', 'Илеана', 'Илианна', 'Илинда', 'Илка', 'Инан', 'Инеса', 'Ира', 'Ирин', 'Ирла', 'Исихия', 'Истилияна', 'Йоана', 'Йоанна', 'Йованка', 'Йоко', 'Йолина', 'Йона', 'Йоника', 'Йорданка', 'Йоханна', 'Кадифейка', 'Калея', 'Калина', 'Калиса', 'Калуда', 'Камея', 'Кануша', 'Карамелита', 'Карина', 'Касиди', 'Катастрофа', 'Катинка', 'Каунка', 'Кветослава', 'Керанка', 'Кети', 'Кино', 'Кирка', 'Китчица', 'Клара', 'Клеуна', 'Клоя', 'Кокимира',
        'Комара', 'Константина', 'Корнелия', 'Костадинка', 'Кралина', 'Красидара', 'Красияна', 'Криси', 'кристабела', 'Кристиана', 'Кристия', 'Кръстанка', 'Ксандриния', 'Кунка', 'Кьнина', 'Лада', 'Лазура', 'Лалка', 'Лариса', 'Лаца', 'Лека', 'Ленче', 'Летисия', 'Либерта', 'Лидийка', 'Лика', 'Лилия', 'Лилянка', 'Линда', 'Лия', 'Лозанка', 'Лорена', 'Лоти', 'Луна', 'Лъчезарка', 'Любина', 'Люблина', 'Любослава', 'Люляна', 'Люсила', 'Лянка', 'Магдалена', 'Мадлен', 'Майя', 'Максимилияна', 'Малена',
        'Малтина', 'Манолина', 'Мара-антоанета', 'Маргит', 'Марен', 'Мари-анри', 'Марийка', 'Маринета', 'Мариотка', 'Мария', 'Мария-елена', 'Мария-Хуана', 'Марлена', 'Маруся', 'Маса', 'Матка', 'Маша', 'Медиха', 'Мелания', 'Мелъди', 'Меропа', 'Миглена', 'Мила', 'Милара', 'милдия', 'Милиана', 'Милост', 'Мимоза', 'Минка', 'Миранза', 'Мирена', 'Миропа', 'Мисла', 'Митошка', 'Михайлена', 'Мишка', 'Младлена', 'Момера', 'Моника', 'Мортадела', 'Мушана', 'Наводненка', 'Надка', 'Найда', 'Нани', 'Настия',
        'Наташа', 'Невена', 'Негрита', 'Неделяна', 'Нейка', 'Нелида', 'Нелла', 'Неолина', 'Нешка', 'Нигрита', 'Никоела', 'Николина', 'Нионила', 'Нона', 'Норка', 'Нурета', 'Огнена', 'Октавия', 'Оливера', 'Омана', 'Орлеана', 'Орхидея', 'Павилия', 'Павлина', 'Палвира', 'Паломина', 'Панда', 'Пантера', 'Парашкевица', 'Парунка', 'Патриотка', 'Паулина', 'Паца', 'Пейолина', 'Пелина', 'Пепелота', 'Периана', 'перуна', 'Петинка', 'Петрийка', 'Петромира', 'Петрушка', 'Пешка', 'Пламена', 'Плодовитка',
        'Полексина', 'Полин', 'Правда', 'Преса', 'Прина', 'Пролетина', 'Простисвета', 'Пупи', 'Първолетка', 'Рада', 'Радиа', 'Радимира', 'Радка', 'Радосвета', 'Радостка', 'Раинка', 'Райничка', 'Рамина', 'Ревка', 'Ренгия', 'Риана', 'Римма', 'Рия', 'Роза', 'Розана', 'Розета', 'Розка', 'Роксана', 'Ромолета', 'Роселина', 'Росислава', 'Ростислава', 'Ружка', 'Румислава', 'Русалия', 'Руска', 'Сабина', 'Савета', 'Салина', 'Санка', 'Сарая', 'Сахория', 'Свежа', 'Светла', 'Светломира', 'Свидна', 'Свободка',
        'Севда', 'севделина', 'Севета', 'Семенарка', 'Сергелинка', 'Сибила', 'Сиена', 'Силви', 'Силвия-александра', 'Силяна', 'Симона', 'Синтия', 'Сисоя', 'Скакалка', 'Славея', 'Славка', 'Сладоледка', 'Смехотерапия', 'Смирна', 'Снежинка', 'Софийка', 'Спасена', 'Спасияна', 'Спирела', 'Стависара', 'Стаматка', 'Станиела', 'Станимирка', 'Сташа', 'Стелина', 'Стефани', 'Стеяна', 'Стоимена', 'Столетка', 'Стоянка', 'Сузи', 'Съвестина', 'Сърменка', 'Таисия', 'тамара', 'Таня', 'Ташимира', 'Теа',
        'Телефонка', 'Темира', 'Теодора', 'Теса', 'Тилиана', 'Тиха', 'Тоанета', 'Толиана', 'Тона', 'Тоницвета', 'Тоска', 'Тошка', 'Трендафила', 'Трифонка', 'Троша', 'Труфана', 'Тръпка', 'Туфка', 'Улиана', 'Урима', 'Фабияна', 'Фатиме', 'Феня', 'Фикия', 'Филипини', 'Фимка', 'Флавия', 'Флорика', 'Фотинка', 'Фронка', 'Фуга', 'Хана', 'Харитония', 'Хенриета', 'Хинка', 'Холи', 'Хранислава', 'Хрисанка', 'Христа', 'Христела', 'Христилияна', 'Христоелена', 'Христя', 'Хубавелка', 'Цанета', 'Царевна',
        'Цветана', 'Цветелина', 'Цветилена', 'Цветлина', 'Цветолилия', 'Цветяна', 'Цеца', 'Цола', 'Цоня', 'Чана', 'Чардафона', 'Чачия', 'Череша', 'Четвърта', 'Чона', 'Чубрина', 'Шана', 'Шена', 'Шехерезада', 'Шинка', 'Щедра', 'Щериана', 'Щефания', 'Щилянка', 'Щтилка', 'Ъгленка', 'Ъчка', 'Юлиена', 'Юлия', 'Юнона', 'Юрита', 'Юстианна', 'Ябленка', 'Явора', 'Ягода', 'Ялислава', 'Яна - Мартина', 'Янина', 'Яниславия', 'Янка', 'Ярка', 'Ясена', 'Ятана'
    );

    protected static $lastNameMale = array(
        'Чанталиев', 'Симеонов', 'Данданов', 'Кърков', 'Братухчев', 'Цветков', 'Иванов', 'Яназов', 'Тодоров', 'Колчев', 'Порязов', 'Келешев', 'Бърборков', 'Дришльов',
        'Макаронски', 'Количков', 'Принов', 'Бодуров', 'Китов', 'Гьоков', 'Кукуряшков', 'Симеонов', 'Балахуров', 'Милачков', 'Европов Кирилов',
        'Площаков', 'Мангъров', 'Хвърчилков', 'Дзезов', 'Ждраков', 'Месаров', 'Тухчиев', 'Топков', 'Яков', 'Иликьов', 'Бурханларски', 'Вражалски', 'Тутурилов', 'Бранков',
        'Зенгинов', 'Чокълтов', 'Фенеров', 'Кучев', 'Възвъзов', 'Кьоров', 'Джогов', 'Пъков', 'Рангелов', 'Чутурков', 'Самсонов', 'Ментешев',
        'Андонов', 'Бумов', 'Мочев', 'Дачев', 'Муев', 'Младенов', 'Тошев', 'Бедринов', 'Тумангелов', 'Негрилов', 'Канчин', 'Миленков', 'Патков',
        'Пондьов', 'Самоходов', 'Четрафилски', 'Смърдански', 'Клатуров', 'Вакрилов', 'Портокалов', 'Прошков', 'Пулев', 'Парашкевов', 'Манавски', 'Чуков',
        'Овнарски', 'Рошльов', 'Пройкова', 'Младенова', 'Кочеткова', 'Кесьов', 'Римпопов', 'Златков', 'Колев', 'Пикянски', 'Николов', 'Цицков',
        'Стойков', 'Каракашев', 'Чуканов', 'Докова', 'Мераков', 'Пеева', 'Педалов', 'Тъпчилещов', 'Въртунински', 'Кодуков', 'Татьозов', 'Токов',
        'Кукуригов', 'Юрганчев', 'Клатикрушев', 'Монтянов', 'Бобев', 'Топчийски', 'Луланков', 'Костов', 'Колипатков', 'Чукчуков', 'Геройски', 'Катъров', 'Кобиларов',
        'Лимонадов', 'Цоцов', 'Поаков', 'Недялков', 'Станишев', 'Йорданов', 'Щърбов', 'Щонов', 'Занов'
    );

    protected static $lastNameFemale = array(
        'Шестакова', 'Кокошкова', 'Градинарова', 'Куртакова', 'Чанлиева', 'Тодорова', 'Пътечкова', 'Скринска', 'Сапунджиева', 'Вампирска', 'Червенкова', 'Васовa', 'Таралингова',
        'Илиева', 'Кривошапкова', 'Чупетловска', 'Катърова', 'Бележкова', 'Мустакова', 'Пръндачка', 'Коритарова', 'Йоткова', 'Сланинкова', 'Мангъфова', 'Шкембова', 'Пенджакова',
        'Пачаръзка', 'Куртажова', 'Плюнкова', 'Многознаева', 'Контопишева', 'Белоконска-Вражалска', 'Кучкуделова', 'Крушовска', 'Пищовколева', 'Сопаджиева', 'Точева-Клопова',
        'Габровлиева', 'Първанова', 'Певецова', 'Курухубева', 'Яркова', 'Плюцова', 'Балканска'
    );

    protected static $titleMale = array('Г-н', 'Др.');
    protected static $titleFemale = array('Г-жа', 'Г-ца', 'Др.');

    /**
     * @param string|null $gender 'male', 'female' or null for any 
     * @example 'Чанталиев'
     */
    public function lastName($gender = null)
    {
        if ($gender === static::GENDER_MALE) {
            return static::lastNameMale();
        } elseif ($gender === static::GENDER_FEMALE) {
            return static::lastNameFemale();
        }
        
        return $this->generator->parse(static::randomElement(static::$lastNameFormat));
    }

    public static function lastNameMale()
    {
        return static::randomElement(static::$lastNameMale);
    }

    public static function lastNameFemale()
    {
        return static::randomElement(static::$lastNameFemale);
    }
}
