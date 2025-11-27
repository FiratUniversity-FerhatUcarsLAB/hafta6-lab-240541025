/*
 * Ad Soyad: Mustafa Murat Hilaloğlu
 * Ogrenci No: 240541025
 * Tarih: 27.11.2025
 * Aciklama: Proje 2 - Sinema Bileti Fiyatlandirma Sistemi
 *
 * Bu program; gun, saat, yas, meslek ve film turu bilgilerini alarak
 * bilet fiyatini hesaplar. Matine, hafta sonu, ogrenci/yasli indirimleri
 * ve film format ucretlerini (3D, IMAX vb.) dikkate alir.
 */

import java.util.Scanner;

public class SinemaBileti {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- SINEMA BİLETİ HESAPLAMA ---");

        // 1. GİRDİLERİN ALINMASI
        System.out.print("Gun (1-7) [1:Pzt ... 7:Paz] : ");
        int gun = scanner.nextInt();

        System.out.print("Saat (8-23)                 : ");
        int saat = scanner.nextInt();

        System.out.print("Yas                         : ");
        int yas = scanner.nextInt();

        System.out.print("Meslek (1:Ogrenci, 2:Ogretmen, 3:Diger) : ");
        int meslek = scanner.nextInt();

        System.out.print("Film Turu (1:2D, 2:3D, 3:IMAX, 4:4DX)   : ");
        int filmTuru = scanner.nextInt();

        // 2. HESAPLAMA ADIMLARI (Metot Çağrıları)

        // Temel fiyatı hesapla (Hafta sonu/içi ve matine durumuna göre)
        double temelFiyat = calculateBasePrice(gun, saat);

        // İndirim oranını belirle (0.20, 0.35 gibi)
        double indirimOrani = calculateDiscount(yas, meslek, gun);

        // Ekstra format ücretini al (3D, IMAX vb.)
        double ekstraUcret = getFormatExtra(filmTuru);

        // Son fiyatı hesapla
        double toplamFiyat = calculateFinalPrice(temelFiyat, indirimOrani, ekstraUcret);

        // 3. SONUÇ RAPORU
        generateTicketInfo(temelFiyat, indirimOrani, ekstraUcret, toplamFiyat);
        
        scanner.close();
    }

    // --- ZORUNLU 7 METOT ---

    /**
     * 1. isWeekend: Gün 6 (Cmt) veya 7 (Paz) ise true döner.
     */
    public static boolean isWeekend(int gun) {
        return (gun == 6 || gun == 7);
    }

    /**
     * 2. isMatinee: Saat 12'den küçükse true döner.
     */
    public static boolean isMatinee(int saat) {
        return saat < 12;
    }

    /**
     * 3. calculateBasePrice: Gün ve saate göre taban fiyatı belirler.
     * Hafta içi matine: 45, Normal: 65
     * Hafta sonu matine: 55, Normal: 85
     */
    public static double calculateBasePrice(int gun, int saat) {
        boolean haftaSonu = isWeekend(gun);
        boolean matine = isMatinee(saat);

        if (haftaSonu) {
            // Ternary operatör kullanımı: Matine ise 55, değilse 85
            return matine ? 55.0 : 85.0;
        } else {
            // Hafta içi
            return matine ? 45.0 : 65.0;
        }
    }

    /**
     * 4. calculateDiscount: Yaş, meslek ve güne göre indirim ORANI döner.
     * İndirimler çakışırsa yaş önceliği veya meslek önceliği kurgulanmıştır.
     */
    public static double calculateDiscount(int yas, int meslek, int gun) {
        // Öncelikle yaş indirimleri (Her gün geçerli)
        if (yas < 12) {
            return 0.25; // %25
        } else if (yas >= 65) {
            return 0.30; // %30
        }

        // Yaş indirimi yoksa meslek indirimlerine bakılır (Switch-Case)
        switch (meslek) {
            case 1: // Öğrenci
                // Pazartesi(1) - Perşembe(4) arası %20
                if (gun >= 1 && gun <= 4) {
                    return 0.20;
                } 
                // Cuma(5) - Pazar(7) arası %15
                else {
                    return 0.15;
                }
            case 2: // Öğretmen
                // Sadece Çarşamba (3) günü %35
                if (gun == 3) {
                    return 0.35;
                } else {
                    return 0.0;
                }
            default: // Diğer
                return 0.0;
        }
    }

    /**
     * 5. getFormatExtra: Film türüne göre ekstra ücreti döner.
     * Switch-Case yapısı kullanılmıştır.
     */
    public static double getFormatExtra(int filmTuru) {
        switch (filmTuru) {
            case 1: return 0.0;  // 2D
            case 2: return 25.0; // 3D
            case 3: return 35.0; // IMAX
            case 4: return 50.0; // 4DX
            default: return 0.0;
        }
    }

    /**
     * 6. calculateFinalPrice: İndirimi uygular ve ekstraları ekler.
     */
    public static double calculateFinalPrice(double temelFiyat, double indirimOrani, double ekstra) {
        double indirimMiktari = temelFiyat * indirimOrani;
        return (temelFiyat - indirimMiktari) + ekstra;
    }

    /**
     * 7. generateTicketInfo: Sonuçları ekrana formatlı yazdırır.
     */
    public static void generateTicketInfo(double temel, double oran, double ekstra, double toplam) {
        System.out.println("\n=== BİLET DETAYLARI ===");
        System.out.printf("Temel Fiyat      : %.2f TL\n", temel);
        
        // İndirim miktarını göstermek için
        double indirimTutari = temel * oran;
        System.out.printf("Indirim (%%%.0f)   : -%.2f TL\n", (oran * 100), indirimTutari);
        
        System.out.printf("Format Ekstra    : +%.2f TL\n", ekstra);
        System.out.println("-------------------------");
        System.out.printf("ODENECEK TUTAR   : %.2f TL\n", toplam);
    }
}
