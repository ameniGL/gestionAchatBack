package com.example.recherche.Product;

import com.example.recherche.Entities.Produit;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private List<Produit> products = new ArrayList<>();

    @Value("${excel.file.path}")
    private String excelFilePath;

    public void loadProducts() {
        products = readExcelFile(excelFilePath);
    }

    public Optional<Produit> findProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst();
    }

    public List<Produit> readExcelFile(String filePath) {
        List<Produit> products = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Skip header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int id = (int) getCellValueAsInt(row.getCell(0)); // Id
                String category = getCellValueAsString(row.getCell(1)); // Category
                String title = getCellValueAsString(row.getCell(2)); // Title
                String brand = getCellValueAsString(row.getCell(3)); // Brand
                String price = getCellValueAsString(row.getCell(4)); // Price
                double rating = getCellValueAsDouble(row.getCell(5)); // Rating
                String shipping = getCellValueAsString(row.getCell(6)); // Shipping
                String image = getCellValueAsString(row.getCell(7)); // Image
                double comment = getCellValueAsDouble(row.getCell(8)); // Comments
                double globalRating = getCellValueAsDouble(row.getCell(9)); // Global Rating
                String url = getCellValueAsString(row.getCell(10)); // URL
                String provider = getCellValueAsString(row.getCell(11)); // Provider

                products.add(new Produit(id, category, title, brand, price, rating, shipping, image, comment, globalRating, url, provider));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return products;
    }

    private int getCellValueAsInt(Cell cell) {
        if (cell == null) {
            return 0;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return (int) cell.getNumericCellValue();
            case STRING:
                try {
                    return Integer.parseInt(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0; // Or handle parsing error
                }
            default:
                return 0;
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }

    private double getCellValueAsDouble(Cell cell) {
        if (cell == null) {
            return 0.0;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return cell.getNumericCellValue();
            case STRING:
                try {
                    return Double.parseDouble(cell.getStringCellValue());
                } catch (NumberFormatException e) {
                    return 0.0;
                }
            default:
                return 0.0;
        }
    }
}