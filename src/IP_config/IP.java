package IP_config;

import javax.swing.*;

public class IP {
    private int[] ip = new int[5];
    private int mask_byte = 0;

    //Читаем IP с клавиатуры
    public void setIP() {
        String sIP = JOptionPane.showInputDialog("Введите IP адрес в формате a.b.c.d/e.\n Где 'e' формат маски ");
        ip = IP_parser.parse_IP(sIP);

    }

    //Сбор строки вывода и вывод
    public void getIPInfo() {
        String mask = "", network = "", broadcast = "";
        int hosts = 0;
        mask = getMask(ip);
        network = getNetwork(ip);
        broadcast = getBroadcast(ip);
        hosts = getHostsAmount(ip[4]).intValue();
        String IP_info = "Маска сети: " + mask + "\n" +
                "Адрес сети: " + network + "\n" +
                "Широковещательный канал: " + broadcast + "\n" +
                "Количество хостов: " + hosts + "\n";
        JOptionPane.showMessageDialog(null, IP_info);

    }

    //Получение последнего ненулевого байта маски
    private int getNonZeroByte(int mask_size) {
        int result = 255;
        int non_zero = mask_size % 8;
        for (int i = 7 - non_zero; i > -1; i--) {
            result -= Math.pow(2, i);
        }
        return (result);
    }

    private Double getHostsAmount(int mask_size) {
        double amount;
        amount = Math.pow(2, 32 - mask_size);
        return (amount);
    }

    //Получение широковещательного канала
    private String getBroadcast(int[] addr) {
        int bytes = 4;
        String broadcast = "";

        for (int i = 0; i < mask_byte; i++) {
            broadcast += addr[i] + ".";
            bytes--;
        }
        if (addr[mask_byte] >= getNonZeroByte(addr[4]) && bytes > 0) {
            while (bytes > 0) {
                broadcast += "255.";
                bytes--;
            }
        } else if (bytes > 0) {
            broadcast += 255 - getNonZeroByte(addr[4]) + ".";
            bytes--;
            while (bytes > 0) {
                broadcast += "255.";
                bytes--;
            }
        }


        return (broadcast.substring(0, broadcast.lastIndexOf(".")));
    }

    // Получение адреса сети
    private String getNetwork(int[] addr) {
        int bytes = 4;
        String network = "";
        int last_mask_byte = getNonZeroByte(addr[4]);
        for (int i = 0; i < mask_byte; i++) {
            network += addr[i] + ".";
            bytes--;
        }
        int last_IP_byte = addr[mask_byte];
        if (bytes > 0) {
            for (int i = 7; last_mask_byte > 0; i--) {
                if (last_IP_byte - Math.pow(2, i) > 0) {
                    last_IP_byte -= Math.pow(2, i);
                }
                last_mask_byte -= Math.pow(2, i);
            }

            network += addr[mask_byte] - last_IP_byte + ".";
            bytes--;
            while (bytes > 0) {
                network += 0 + ".";
                bytes--;
            }
        }
        return (network.substring(0, network.lastIndexOf(".")));


    }


    //Получение маски сети
    private String getMask(int[] addr) {
        int bytes = 4;
        String mask = "";
        int mask_size = addr[4];

        if (mask_size == 32) {
            return ("255.255.255.255");
        }

        for (int i = 0; i < mask_size / 8; i++) {
            mask += "255.";
            bytes--;
            mask_byte++;
        }

        mask += getNonZeroByte(mask_size) + ".";
        bytes--;

        while (bytes != 0) {
            bytes--;
            mask += "0.";
        }

        mask = mask.substring(0, mask.lastIndexOf("."));

        return (mask);
    }
}
