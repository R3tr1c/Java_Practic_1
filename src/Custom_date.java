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
    //функционал класса
    public void set_new_date_format(String input_format){
        check_input_format(input_format);
        date_format = input_format;
    }
    //функционал класса
    public String get_date_string_in_format () {
        String compiled_date_in_format = date_format;
        StringBuilder buff_str = new StringBuilder();
        int count_month_txt_symbols = count_format_symbols(date_format, 'l'),
            count_year_num_symbols  = count_format_symbols(date_format, 'y');

        compiled_date_in_format = add_day_n_month_output(compiled_date_in_format, "d");
        compiled_date_in_format = add_day_n_month_output(compiled_date_in_format, "m");

        if (date_format.contains("l")){
            if (count_month_txt_symbols > month_text[parted_date[1] - 1].length()) count_month_txt_symbols = month_text[parted_date[1] - 1].length();
            buff_str = new StringBuilder(month_text[parted_date[1] - 1].substring(0, count_month_txt_symbols));
            compiled_date_in_format =  compiled_date_in_format.replace(compiled_date_in_format.substring(compiled_date_in_format.indexOf('l'), compiled_date_in_format.lastIndexOf('l') + 1), buff_str.toString());
            buff_str = new StringBuilder();
        }

        if (date_format.contains("y")){
            if (count_year_num_symbols > Integer.toString(parted_date[2]).length()){
                buff_str.append(parted_date[2]);
                while (buff_str.length() < count_year_num_symbols){
                    buff_str.insert(0, "0");
                }
            }
            else if (count_year_num_symbols < Integer.toString(parted_date[2]).length()){
                for (int i = Integer.toString(parted_date[2]).length() - 1; i >= count_year_num_symbols; i-- ){
                    buff_str.insert(0, Integer.toString(parted_date[2]).charAt(i));
                }
            }
            else buff_str = new StringBuilder(Integer.toString(parted_date[2]));

            compiled_date_in_format =  compiled_date_in_format.replace( compiled_date_in_format.substring(compiled_date_in_format.indexOf('y'), compiled_date_in_format.lastIndexOf('y') + 1), buff_str.toString());

        }
        return compiled_date_in_format;
    }

    //хранилище для даты
    private int[] parted_date;

    //тексты месяцев
    private final String[] month_text = { "Января",
                                          "Февраля",
                                          "Марта",
                                          "Апреля",
                                          "Мая",
                                          "Июня",
                                          "Июля",
                                          "Августа",
                                          "Сентября",
                                          "Октября",
                                          "Ноября",
                                          "Декабря"};

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
        if (!(((parted_date[0] > 0 && parted_date[0] < 32) && (parted_date[1] > 0 && parted_date[1] < 13)) &&
              (parted_date[1] != 2 || parted_date[0] != 29 || leap_year_check(parted_date[2])))){
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
        //Проверить отсутствие разделителей
        check_illegal_format_delims(format_to_check);
        //Проверить неучтенные символы
        check_illegal_format_content(format_to_check);
        //Проверить двузначность дня месяца при формате в однозначный
        check_illegal_day_format(format_to_check);
        //Проверить конвертацию номера месяца в однозначную + трехзначного и более текста месяца
        check_illegal_month_format(format_to_check);
        //Проверить однозначность года при формате в длину меньше четырехназначной + запрос года длиной 1 символ
        check_illegal_year_format(format_to_check);
    }
    //побочный
    private void check_illegal_format_delims(String format_to_check) throws CustomDateIllegalFormatInput{
        if (((format_to_check.contains("d") && (format_to_check.contains("m") || format_to_check.contains("l") || format_to_check.contains("y"))) ||
                (format_to_check.contains("m") && (format_to_check.contains("d") || format_to_check.contains("l") || format_to_check.contains("y"))) ||
                (format_to_check.contains("l") && (format_to_check.contains("m") || format_to_check.contains("d") || format_to_check.contains("y"))) ||
                (format_to_check.contains("y") && (format_to_check.contains("m") || format_to_check.contains("l") || format_to_check.contains("d")))) &&
                (!(format_to_check.contains(".") || format_to_check.contains("/") || format_to_check.contains("\\") || format_to_check.contains("-") || format_to_check.contains("_")))) {
               throw new CustomDateIllegalFormatInput("Ошибка введенного формата: отсутствуют разрешенные разделители!");
           }
    }
    //побочный
    private void check_illegal_format_content(String format_to_check) throws CustomDateIllegalFormatInput{
        for (int i = 0; i < format_to_check.length(); i++)
            if (format_to_check.charAt(i) != 'd' || format_to_check.charAt(i) != 'm' || format_to_check.charAt(i) != 'l' || format_to_check.charAt(i) != 'y' ||
                    format_to_check.charAt(i) != '.' || format_to_check.charAt(i) != '/' || format_to_check.charAt(i) != '\\' || format_to_check.charAt(i) != '-' )
                throw new CustomDateIllegalFormatInput("Ошибка введенного формата: присутствуют неучтенные символы!");
    }
    //побочный
    private void check_illegal_day_format(String format_to_check) throws CustomDateIllegalFormatInput{
        int count_day_symbols_in_format = count_format_symbols(format_to_check, 'd');
        if (Integer.toString(parted_date[0]).length() > 1 && count_day_symbols_in_format == 1)
            throw new CustomDateIllegalFormatInput("Ошибка введенного формата: потеря репрезентативности номера дня месяца!");
    }
    //побочный
    private void check_illegal_month_format(String format_to_check) throws CustomDateIllegalFormatInput{
        int count_month_num_symbols_in_format = count_format_symbols(format_to_check, 'm'),
            count_month_txt_symbols_in_format = count_format_symbols(format_to_check, 'l');
        if ((Integer.toString(parted_date[1]).length() > 1 && count_month_num_symbols_in_format == 1) ||
            (count_month_txt_symbols_in_format < 3 && count_month_txt_symbols_in_format > 0) )
            throw new CustomDateIllegalFormatInput("Ошибка введенного формата: потеря репрезентативности месяца!");
    }
    //побочный
    private void check_illegal_year_format(String format_to_check) throws CustomDateIllegalFormatInput{
        int count_year_symbols_in_format = count_format_symbols(format_to_check, 'y');
        if (Integer.toString(parted_date[2]).length() > 1 && (count_year_symbols_in_format == 1 ||
            (count_year_symbols_in_format == 2 && (parted_date[2] / 1000 > 0 || parted_date[2] / 100 > 0 ) && !(parted_date[2] / 1000 == 2) ) ||
            (count_year_symbols_in_format == 3 && parted_date[2] / 1000 > 0) ))
            throw new CustomDateIllegalFormatInput("Ошибка введенного формата: потеря репрезентативности номера года!");
    }
    //побочный
    private int count_format_symbols(String format_to_count, char counting_symbol){
        int symbols_count = 0;
        for(char str_char : format_to_count.toCharArray())
            if (str_char == counting_symbol) symbols_count++;
        return symbols_count;
    }
    //побочный повторяемый
    private String add_day_n_month_output(String compiled_date_in_format, String date_part_type){
        int count_day_symbols = count_format_symbols(date_format, date_part_type.charAt(0));
        StringBuilder buff_str = new StringBuilder();
        int i = 0;
        if (date_part_type.equals("m")) i = 1;
        if (date_format.contains(date_part_type)){
            buff_str.append(parted_date[i]);
            while (buff_str.length() < count_day_symbols){
                buff_str.insert(0, "0");
            }
            compiled_date_in_format = compiled_date_in_format.replace( compiled_date_in_format.substring(compiled_date_in_format.indexOf(date_part_type),
                                                                       compiled_date_in_format.lastIndexOf(date_part_type) + 1), buff_str.toString());
        }
        return compiled_date_in_format;
    }
}


