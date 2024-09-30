package application.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import application.model.Product;

public class ReceiptPrinter implements Printable {

	private List<Product> products;

	public void printReceipt(List<Product> products) {
		this.products = products;
		PrinterJob job = PrinterJob.getPrinterJob();

		// "Aclas Printer" adındaki yazıcıyı bul
		PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
		for (PrintService printService : printServices) {
			if (printService.getName().equalsIgnoreCase("Aclas Printer")) {
				try {
					job.setPrintService(printService); // Yazıcıyı ayarla

					// Kağıt boyutlarını ayarla
					PageFormat pageFormat = job.defaultPage();
					Paper paper = new Paper();

					double width = 80; // mm
					double height = 99999999; // mm

					// Dönüştürme: 1 inç = 25.4 mm
					double widthInPoints = width / 25.4 * 72;
					double heightInPoints = height / 25.4 * 72;

					// Kağıt boyutlarını ayarla
					paper.setSize(widthInPoints, heightInPoints);
					paper.setImageableArea(0, 0, widthInPoints, heightInPoints);

					pageFormat.setPaper(paper);
					job.setPrintable(this, pageFormat);

					job.print(); // Yazdırma işlemini başlat
				} catch (PrinterException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 1) {
			return NO_SUCH_PAGE; // Birden fazla sayfa desteklenmiyor
		}

		Graphics2D g2d = (Graphics2D) graphics;
		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String formattedDateTime = now.format(formatter);

		// Yazdırılacak metni ayarla

		g2d.setColor(Color.BLACK);
		int y = 15; // Başlangıç yüksekliği

		g2d.setFont(new Font("Monospaced", Font.BOLD, 18));
		g2d.drawString("NOVA MARKET & AVM", 10, y);
		y += 20;
		g2d.setFont(new Font("Monospaced", Font.BOLD, 10));
		g2d.drawString("   SİPARİŞ:   537 672 93 69", 10, y);
		y += 15;
		g2d.drawString("   TELEFON:   530 832 70 20", 10, y);
		y += 18;
		g2d.drawString("   Tarih:  " + formattedDateTime, 10, y);
		y += 25;
		g2d.setFont(new Font("Monospaced", Font.BOLD, 10));
		g2d.drawString("MİKTAR     FİYAT      TOPLAM", 10, y);
		y += 10;
		g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
		g2d.drawString("------------------------------", 10, y);
		y += 10;

		for (Product item : products) {
			g2d.setFont(new Font("Monospaced", Font.BOLD, 12));
			g2d.drawString(item.getProductName(), 10, y);
			y += 12;

			String productLine = String.format("%-10.2f %-10.2f %-10.2f", item.getQuantity(), item.getUnitPrice(),
					(item.getQuantity() * item.getUnitPrice()));
			g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
			g2d.drawString(productLine, 10, y);
			y += 15;

		}
		g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
		g2d.drawString("------------------------------", 10, y);
		y += 15;

		double totalPrice = 0;
		for (Product item : products) {

			totalPrice += (item.getQuantity() * item.getUnitPrice());

		}

		g2d.setFont(new Font("Monospaced", Font.BOLD, 12));
		g2d.drawString("Toplam Tutar: " + totalPrice + "TL", 10, y);
		y += 15;
		g2d.setFont(new Font("Monospaced", Font.PLAIN, 10));
		g2d.drawString("------------------------------", 10, y);
//		y += 15;
//		g2d.drawString("Ödeme Şekli: Nakit", 10, y);
//		y += 15;
//		g2d.drawString("------------------------------", 10, y);
		y += 15;
		g2d.setFont(new Font("Monospaced", Font.BOLD, 10));
		g2d.drawString("    ** Mali Değeri Yoktur **    ", 10, y);
		y += 15;
		g2d.setFont(new Font("Monospaced", Font.BOLD, 12));
		g2d.drawString("Bizi Tercih Ettiğiniz İçin  ", 10, y);
		y += 11;
		g2d.drawString("     Teşekkür Ederiz    ", 10, y);
		y += 30;

		return PAGE_EXISTS;
	}

}