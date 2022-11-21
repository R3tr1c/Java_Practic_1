import java.util.regex.Pattern;

public class Custom_date {

    //конструктор с исключением по комплексной проверке ввода
    public Custom_date(String input_date_value){
        check_string_correct_date(input_date_value);
        parted_date = string_date_partition(input_date_value);
        date_format = "dd.mm.yyyy";
    }

    //функционал класса
    public int get_day_of_week_num(){
        int month_trans = parted_date[1] + 10, year_trans = parted_date[2];
        if (month_trans > 12) {
            month_trans -= 12;
        }
        if (month_trans == 11 || month_trans == 12) {
            year_trans--;
        }
        return (parted_date[0] + (int) (Math.floor((13 * month_trans - 1) / 5)) + year_trans +
                (int) (Math.floor(year_trans / 4)) - (int) (Math.floor(year_trans / 100)) +
                (int) (Math.floor(year_trans / 400))) % 7;
    }

    //функционал класса
    public void show_day_of_week_rus() {
        int day_of_week_num = get_day_of_week_num();
        System.out.print("День недели: ");
        switch (day_of_week_num) {
            case 0 -> System.out.println("воскресенье");
            case 1 -> System.out.println("понедельник");
            case 2 -> System.out.println("вторник");
            case 3 -> System.out.println("среда");
            case 4 -> System.out.println("четверг");
            case 5 -> System.out.println("пятница");
            case 6 -> System.out.println("суббота");
        }
    }

    //функционал класса
    public int get_days_per_month_value(){
        int[] day_list = { 28, 29, 30, 31 };
        int curr_day_count = 0;
        switch (parted_date[1]){
            case 2:
                if (!leap_year_check(parted_date[2])) { curr_day_count = day_list[0]; }
                else                                  { curr_day_count = day_list[1]; }
                break;
            case 4, 6, 9, 11:
                curr_day_count = day_list[2];
                break;
            case 1, 3, 5, 7, 8, 10, 12:
                curr_day_count = day_list[3];
                break;
        }
        return curr_day_count;
    }

    //функционал класса
    public void show_days_per_month_value(){
        int curr_day_count = get_days_per_month_value();
        System.out.printf("Общее количество дней в месяце: %d", curr_day_count);
    }

    //функционал класса
    public void show_is_leap_year(){
        boolean check_leap_year = leap_year_check(parted_date[2]);
        if (check_leap_year) { System.out.println("Год: високосный.");}
        else                 { System.out.println("Год: не-високосный."); }
    }

    //функционал класса
    public void show_date_complex_information(){
        System.out.printf("Комплексная информация о дате:\nДень: %d\n", parted_date[0]);
        show_day_of_week_rus();
        System.out.printf("Месяц: %d\nГод: %d\n", parted_date[1], parted_date[2]);
        show_is_leap_year();
    }

    public void set_new_date_format(String input_format){
        //Добавить проверки корректность формата и доступность для обрабатываемой даты
        check_input_format(input_format);
        date_format = input_format;
    }

    public String get_date_string_in_format () {

        return new String("");
    }

    //хранилище для даты
    private int[] parted_date;

    private String date_format;

    //побочный комплексный
    private void check_string_correct_date(String value_to_check) {
        check_string_input_format(value_to_check);
        check_date_is_legal(value_to_check);
    }

    //побочный
    private void check_string_input_format(String value_to_check) throws CustomDateIllegalInput {
        Pattern string_date_inp_pattern = Pattern.compile("^\\d{2}\\W\\d{2}\\W\\d{4}$", Pattern.CASE_INSENSITIVE);
        if (!(Pattern.matches(string_date_inp_pattern.pattern(), value_to_check) &&
                value_to_check.length() == 10 &&
                value_to_check.charAt(2) == '.' &&
                value_to_check.charAt(5) == '.')){
            throw new CustomDateIllegalInput("Ошибка ввода даты: неверный формат!");
        }
    }

    //побочный с повторяемой составляющей
    private void check_date_is_legal(String value_to_check) throws CustomDateIllegalInput {
        int[] parted_date = string_date_partition(value_to_check);
        if (!(((parted_date[0] > 0 && parted_date[0] < 32) && (parted_date[1] > 0 && parted_date[1] < 13)) && (parted_date[1] != 2 || parted_date[0] != 29 || leap_year_check(parted_date[2])))){
            throw new CustomDateIllegalInput("Ошибка ввода даты: введенной даты не существует.");
        }
    }

    //побочный повторяемый
    private int[] string_date_partition(String parting_string) {
        int[] parted_date = new int[3];
        parted_date[0] = Integer.parseInt(parting_string.substring(0, 2));
        parted_date[1] = Integer.parseInt(parting_string.substring(3, 5));
        parted_date[2] = Integer.parseInt(parting_string.substring(6));
        return parted_date;
    }

    //побочный повторяемый
    private boolean leap_year_check(int checking_year) {
        return checking_year % 4 == 0 && (checking_year % 400 == 0 || checking_year % 100 != 0);
    }
    //побочный комплексный
    private void check_input_format (String format_to_check) {
        //Проверить неучтенные символы
        check_illegal_format_content(format_to_check);
        //Проверить двузначность дня месяца при формате в однозначный
        check_illegal_day_format(format_to_check);
        //Проверить конвертацию номера месяца в однозначную + трехзначного и более текста месяца
        check_illegal_month_format(format_to_check);
        //Проверить однозначность года при формате в длину меньше четырехназначной + запрос года длиной 1 символ
        check_illegal_year_format(format_to_check);
    }
    private void check_illegal_format_content(String format_to_check) throws CustomDateIllegalFormatInput{
           if (!(format_to_check.contains(".") || format_to_check.contains("/") ||
                 format_to_check.contains("\\") || format_to_check.contains("-") ||
                 format_to_check.contains(" "))){
               throw new CustomDateIllegalFormatInput("Ошибка введенного формата: отсутствуют разрешенные разделители!");
           }
    }
    private void check_illegal_day_format(String format_to_check) throws CustomDateIllegalFormatInput{
        int count_day_symbols_in_format = 0;
        for(char str_char : format_to_check.toCharArray()){
            if (str_char == 'd') count_day_symbols_in_format++;
        }
        if (Integer.toString(parted_date[0]).length() > 1 && count_day_symbols_in_format == 1)
            throw new CustomDateIllegalFormatInput("Ошибка введенного формата: потеря репрезентативности номера дня месяца!");
    }

    private void check_illegal_month_format(String format_to_check) throws CustomDateIllegalFormatInput{
        int count_month_num_symbols_in_format = 0,
            count_month_txt_symbols_in_format = 0;
        for(char str_char : format_to_check.toCharArray()){
            if (str_char == 'm') count_month_num_symbols_in_format++;
            if (str_char == 'l') count_month_txt_symbols_in_format++;
        }
        if ((Integer.toString(parted_date[1]).length() > 1 && count_month_num_symbols_in_format == 1) || (count_month_txt_symbols_in_format < 3) )
            throw new CustomDateIllegalFormatInput("Ошибка введенного формата: потеря репрезентативности месяца!");
    }

    private void check_illegal_year_format(String format_to_check) throws CustomDateIllegalFormatInput{
        int count_year_symbols_in_format = 0;
        for(char str_char :  format_to_check.toCharArray() )
            if (str_char == 'y') count_year_symbols_in_format++;
        if (Integer.toString(parted_date[2]).length() > 1 && (count_year_symbols_in_format == 1 || (count_year_symbols_in_format == 2 && (parted_date[2] / 1000 > 0 || parted_date[2] / 100 > 0 ) && !(parted_date[2] / 1000 == 2) ) || (count_year_symbols_in_format == 3 && parted_date[2] / 1000 > 0) ))
            throw new CustomDateIllegalFormatInput("Ошибка введенного формата: потеря репрезентативности номера года!");
    }

}


