/*
 * Ad Soyad: Mustafa Murat Hilaloğlu
 * Ogrenci No: 240541025
 * Tarih: 27.11.2025
 * Aciklama: Proje 1 - Ogrenci Not Değerlendirme Sistemi
 *
 * Bu program bir ogrencinin vize, final ve odev notlarini alarak;
 * ortalama hesaplar, harf notunu belirler, gecme/kalma durumunu,
 * onur listesi ve butunleme hakkini kontrol eder ve raporlar.
 */

import java.util.Scanner;

public class OgrenciNotSistemi {

    public static void main(String[] args) {
        // Kullanıcıdan veri almak için Scanner nesnesi oluşturuluyor
        Scanner scanner = new Scanner(System.in);

        // Kullanıcı bilgilendirme ve veri girişi
        System.out.println("Lütfen notları giriniz (0-100 arası):");

        System.out.print("Vize: ");
        double vize = scanner.nextDouble();

        System.out.print("Final: ");
        double finalNot = scanner.nextDouble();

        System.out.print("Ödev: ");
        double odev = scanner.nextDouble();

        // --- HESAPLAMA METOTLARININ ÇAĞRILMASI ---

        // 1. Ortalama hesaplama metodunu çağırır ve sonucu değişkene atar
        double ortalama = calculateAverage(vize, finalNot, odev);

        // 2. Geçme durumunu kontrol eden metodu çağırır (true/false döner)
        boolean gectiMi = isPassingGrade(ortalama);

        // 3. Harf notunu hesaplayan metodu çağırır (String döner)
        String harfNotu = getLetterGrade(ortalama);

        // 4. Onur listesi kontrolü (Notların hepsi ve ortalama parametre olarak gider)
        boolean onurListesi = isHonorList(ortalama, vize, finalNot, odev);

        // 5. Bütünleme hakkı kontrolü
        boolean butunlemeHakki = hasRetakeRight(ortalama);

        // --- SONUÇLARIN EKRANA YAZDIRILMASI ---

        System.out.println("\n=== OGRENCI NOT RAPORU ===");
        
        // printf ile ondalıklı sayıları virgülden sonra 1 hane olacak şekilde formatlıyoruz (%.1f)
        System.out.printf("Vize Notu    : %.1f\n", vize);
        System.out.printf("Final Notu   : %.1f\n", finalNot);
        System.out.printf("Odev Notu    : %.1f\n", odev);
        System.out.println("------------------------------");
        System.out.printf("Ortalama     : %.1f\n", ortalama);
        System.out.println("Harf Notu    : " + harfNotu);

        // Ternary Operator (? :) kullanımı: Koşul doğruysa ilk ifade, yanlışsa ikinci ifade yazılır
        System.out.println("Durum        : " + (gectiMi ? "GECTI" : "KALDI"));
        System.out.println("Onur Listesi : " + (onurListesi ? "EVET" : "HAYIR"));
        System.out.println("Butunleme    : " + (butunlemeHakki ? "VAR" : "YOK"));
        
        scanner.close(); // Kaynak sızıntısını önlemek için scanner kapatılır
    }

    // --- ZORUNLU METOT TANIMLAMALARI ---

    /**
     * Vize (%30), Final (%40) ve Ödev (%30) ağırlıklarına göre ortalamayı hesaplar.
     */
    public static double calculateAverage(double vize, double finalNot, double odev) {
        return (vize * 0.30) + (finalNot * 0.40) + (odev * 0.30);
    }

    /**
     * Ortalama 50 ve üzerindeyse true (Geçti), altındaysa false (Kaldı) döndürür.
     * İlişkisel operatör (>=) kullanılmıştır.
     */
    public static boolean isPassingGrade(double grade) {
        return grade >= 50.0;
    }

    /**
     * Ortalamaya göre harf notunu belirler.
     * if-else-if merdiven yapısı kullanılmıştır.
     */
    public static String getLetterGrade(double grade) {
        if (grade >= 90) {
            return "A";
        } else if (grade >= 80) {
            return "B";
        } else if (grade >= 70) {
            return "C";
        } else if (grade >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    /**
     * Onur listesi kuralı: Ortalama >= 85 VE (&&) hiçbir not 70'in altında olmamalı.
     * Mantıksal operatörler (&&) ile tüm şartların aynı anda sağlanması kontrol edilir.
     */
    public static boolean isHonorList(double grade, double vize, double finalNot, double odev) {
        return (grade >= 85) && (vize >= 70) && (finalNot >= 70) && (odev >= 70);
    }

    /**
     * Bütünleme kuralı: Ortalama 40'a eşit/büyük VE 50'den küçük olmalı.
     */
    public static boolean hasRetakeRight(double grade) {
        return (grade >= 40) && (grade < 50);
    }
}
