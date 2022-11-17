import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String inp_str = "";
        System.out.println("Введите дату в начальном формате dd.mm.yyyy [введите 0 для возврата]: ");
        Scanner main_inp_scan = new Scanner(System.in);
        inp_str = main_inp_scan.next();
        if (inp_str.length() == 1 && inp_str.charAt(0) == '0'){
            System.out.println("Выход...");
        } else {

            Custom_date test_obj_custom_date = new Custom_date(inp_str);

            int test_get_day_of_week_num = test_obj_custom_date.get_day_of_week_num();
            System.out.printf("\nТест: метод получения номера дня недели: %d", test_get_day_of_week_num);

            System.out.println("\n\nТест: метод отображения дня недели текстом:");
            test_obj_custom_date.show_day_of_week_rus();

            int test_get_days_per_month_value = test_obj_custom_date.get_days_per_month_value();
            System.out.printf("\nТест: метод получения кол-ва дней в месяце: %d", test_get_days_per_month_value);

            System.out.println("\n\nТест: метод отображения кол-ва дней в месяце:");
            test_obj_custom_date.show_days_per_month_value();

            System.out.println("\n\nТест: метод отображения \"високосности\" года:");
            test_obj_custom_date.show_is_leap_year();

            System.out.println("\nТест: метод отображения комплексной информации по дате: ");
            test_obj_custom_date.show_date_complex_information();
        }

    }
}

