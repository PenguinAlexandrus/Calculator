package IP_config;
import javax.swing.*;

public class IP_parser {
    //
    //сообщение об ощибке
    //
    private static void ERROR_message() {
        JOptionPane.showMessageDialog(null, "Введите коректный IP адрес!!!");
    }

    //функция парсера
    public static int[] parse_IP(String string_IP) {
        int[] arr_IP = new int[5];

        //Разделяем строку с IP по байтам и отеляем маску
        String[] string_arr_IP = string_IP.split("[/.]");

        //Преобразовываем рабитую строку IP в массив чисел
        for (int i = 0; i < 5; i++) {
            try {
                arr_IP[i] = Integer.parseInt(string_arr_IP[i]);
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                ERROR_message();
                return null;
            }
        }
        //"Проверка на дурака"
        for (int i = 0; i < 5; i++) {
            if (arr_IP[i] > 255 || arr_IP[i] < 0 || arr_IP[4] > 32|| string_arr_IP.length > 5) {
                ERROR_message();
                break;
            }
        }

            return arr_IP;
    }
}

