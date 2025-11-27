/*
 * Ad Soyad: Mustafa Murat Hilaloğlu
 * Ogrenci No: 240541025
 * Tarih: 27.11.2025
 * Aciklama: Proje 3 - Akilli Restoran Siparis Sistemi
 *
 * Bu program; ana yemek, baslangic, icecek ve tatli secimlerini alir.
 * Combo menu, happy hour, ogrenci indirimi ve 200 TL uzeri indirimleri
 * belirli bir sirayla (kademeli olarak) uygular ve hesap fisi olusturur.
 */

import java.util.Scanner;

public class RestoranSistemi {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- AKILLI RESTORAN SIPARIS SISTEMI ---");
        System.out.println("Lutfen secimlerinizi kod numarasi ile yapiniz (Yoksa 0 giriniz).");

        // --- 1. SİPARİŞ GİRİŞLERİ ---
        
        // Ana Yemek
        System.out.println("\n[1:Tavuk 85TL, 2:Kebap 120TL, 3:Levrek 110TL, 4:Manti 65TL]");
        System.out.print("Ana Yemek Secimi : ");
        int anaYemekSecim = scanner.nextInt();
        double anaYemekFiyat = getMainDishPrice(anaYemekSecim);

        // Başlangıç
        System.out.println("[1:Corba 25TL, 2:Humus 45TL, 3:Borek 55TL]");
        System.out.print("Baslangic Secimi : ");
        int baslangicSecim = scanner.nextInt();
        double baslangicFiyat = getAppetizerPrice(baslangicSecim);

        // İçecek
        System.out.println("[1:Kola 15TL, 2:Ayran 12TL, 3:Meyve Suyu 35TL, 4:Limonata 25TL]");
        System.out.print("Icecek Secimi    : ");
        int icecekSecim = scanner.nextInt();
        double icecekFiyat = getDrinkPrice(icecekSecim);

        // Tatlı
        System.out.println("[1:Kunefe 65TL, 2:Baklava 55TL, 3:Sutlac 35TL]");
        System.out.print("Tatli Secimi     : ");
        int tatliSecim = scanner.nextInt();
        double tatliFiyat = getDessertPrice(tatliSecim);

        // Diğer Bilgiler
        System.out.print("\nSaat (8-23)      : ");
        int saat = scanner.nextInt();

        System.out.print("Ogrenci misiniz? (E/H) : ");
        // Scanner bug'ını önlemek için next() kullanıyoruz
        String ogrenciYanit = scanner.next(); 
        boolean ogrenciMi = ogrenciYanit.equalsIgnoreCase("E");

        System.out.print("Hangi gun? (1-7) : ");
        int gun = scanner.nextInt();

        // --- 2. DURUM KONTROLLERİ VE HESAPLAMA ---

        // Ara Toplam
        double araToplam = anaYemekFiyat + baslangicFiyat + icecekFiyat + tatliFiyat;

        // Boolean kontroller
        boolean anaYemekVar = anaYemekFiyat > 0;
        boolean icecekVar = icecekFiyat > 0;
        boolean tatliVar = tatliFiyat > 0;

        // Combo Kontrolü
        boolean comboVar = isComboOrder(anaYemekVar, icecekVar, tatliVar);
        
        // İndirim Hesaplama Metodu Çağrısı
        // Not: Örnek senaryodaki karmaşık hesaplama için gerekli tüm verileri gönderiyoruz.
        double toplamIndirim = calculateDiscount(araToplam, comboVar, ogrenciMi, saat, icecekFiyat, gun);

        // Net Tutar
        double odenecekTutar = araToplam - toplamIndirim;

        // Bahşiş Hesaplama
        double bahsis = calculateServiceTip(odenecekTutar);

        // --- 3. FİŞ ÇIKTISI ---
        
        System.out.println("\n==================================");
        System.out.println("      HESAP DETAYLARI");
        System.out.println("==================================");
        
        if(anaYemekVar) System.out.printf("Ana Yemek   : %.2f TL\n", anaYemekFiyat);
        if(baslangicFiyat > 0) System.out.printf("Baslangic   : %.2f TL\n", baslangicFiyat);
        if(icecekVar)   System.out.printf("Icecek      : %.2f TL\n", icecekFiyat);
        if(tatliVar)    System.out.printf("Tatli       : %.2f TL\n", tatliFiyat);
        
        System.out.println("----------------------------------");
        System.out.printf("ARA TOPLAM  : %.2f TL\n", araToplam);
        System.out.printf("TOPLAM IND. : -%.2f TL\n", toplamIndirim);
        System.out.println("----------------------------------");
        System.out.printf("ODENECEK    : %.2f TL\n", odenecekTutar);
        System.out.printf("ONERILEN BAHŞİŞ (%%10): %.2f TL\n", bahsis);

        scanner.close();
    }

    // --- ZORUNLU 8 METOT ---

    // 1. Ana Yemek Fiyatı
    public static double getMainDishPrice(int secim) {
        switch (secim) {
            case 1: return 85.0;  // Izgara Tavuk
            case 2: return 120.0; // Adana Kebap
            case 3: return 110.0; // Levrek
            case 4: return 65.0;  // Manti
            default: return 0.0;
        }
    }

    // 2. Başlangıç Fiyatı
    public static double getAppetizerPrice(int secim) {
        switch (secim) {
            case 1: return 25.0; // Corba
            case 2: return 45.0; // Humus
            case 3: return 55.0; // Sigara Boregi
            default: return 0.0;
        }
    }

    // 3. İçecek Fiyatı
    public static double getDrinkPrice(int secim) {
        switch (secim) {
            case 1: return 15.0; // Kola
            case 2: return 12.0; // Ayran
            case 3: return 35.0; // Meyve Suyu
            case 4: return 25.0; // Limonata
            default: return 0.0;
        }
    }

    // 4. Tatlı Fiyatı
    public static double getDessertPrice(int secim) {
        switch (secim) {
            case 1: return 65.0; // Kunefe
            case 2: return 55.0; // Baklava
            case 3: return 35.0; // Sutlac
            default: return 0.0;
        }
    }

    // 5. Combo Kontrolü: Ana Yemek + İçecek + Tatlı varsa true döner
    public static boolean isComboOrder(boolean anaVar, boolean icecekVar, boolean tatliVar) {
        return anaVar && icecekVar && tatliVar;
    }

    // 6. Happy Hour Kontrolü: Saat 14 ile 17 arası (dahil)
    public static boolean isHappyHour(int saat) {
        return saat >= 14 && saat <= 17;
    }

    // 7. İndirim Hesaplama (En Karmaşık Kısım)
    // Parametreleri projeye uygun şekilde genişleterek doğru hesaplamayı sağlıyoruz.
    public static double calculateDiscount(double tutar, boolean combo, boolean ogrenci, int saat, double icecekFiyat, int gun) {
        double toplamIndirim = 0.0;
        double kalanTutar = tutar; // İndirimler sırayla düşülerek ilerlenir

        // A. Combo İndirimi (%15 - Toplam Tutar Üzerinden)
        if (combo) {
            double comboIndirimi = tutar * 0.15;
            toplamIndirim += comboIndirimi;
            kalanTutar -= comboIndirimi; // Kümülatif etki için düşüyoruz
        }

        // B. Happy Hour İndirimi (%20 - Sadece İçecek Üzerinden)
        if (isHappyHour(saat) && icecekFiyat > 0) {
            double happyIndirim = icecekFiyat * 0.20;
            toplamIndirim += happyIndirim;
            kalanTutar -= happyIndirim;
        }

        // C. Öğrenci İndirimi (%10 - Hafta içi)
        // Örnek senaryoda öğrenci indirimi kalan tutar üzerinden hesaplanmıştır.
        if (ogrenci && gun <= 5) { // 1-5 arası hafta içi
            double ogrenciIndirimi = kalanTutar * 0.10;
            toplamIndirim += ogrenciIndirimi;
        } 
        // D. 200 TL Üzeri İndirimi (%10)
        // Eğer öğrenci değilse ve tutar 200 üzerindeyse bu çalışır (Çakışma kuralı)
        else if (tutar > 200.0) {
            double buyukSiparisIndirimi = kalanTutar * 0.10;
            toplamIndirim += buyukSiparisIndirimi;
        }

        return toplamIndirim;
    }

    // 8. Bahşiş Hesaplama
    public static double calculateServiceTip(double tutar) {
        return tutar * 0.10; // %10 Bahşiş
    }
}
