package indi.yuluo.web.util;


import com.yyby.exception.ServiceException;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import static org.apache.poi.ss.usermodel.ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE;

public final class ExcelUtils {

    private ExcelUtils() {
    }

    /**
     * 在 Excel 文件中添加签名
     *
     * @param coordinate 需要放置图片的 excel 文件坐标
     * @param signatureImgath 签名图片路径
     * @param excelPath  需要添加签名的 excel 文件路径
     */
    public static void addSignature(List<Integer> coordinate, String signatureImgath, String excelPath) {

        int params1 = coordinate.get(0);
        int params2 = coordinate.get(1);
        int params3 = coordinate.get(2);
        int params4 = coordinate.get(3);
        int params5 = coordinate.get(4);
        int params6 = coordinate.get(5);
        int params7 = coordinate.get(6);
        int params8 = coordinate.get(7);

        FileOutputStream fileOut = null;
        ByteArrayOutputStream byteArrayOut = null;
        FileInputStream fileInputStream = null;
        try {

            byteArrayOut = new ByteArrayOutputStream();

            BufferedImage bufferImg = ImageIO.read(new File(signatureImgath));
            ImageIO.write(bufferImg, "png", byteArrayOut);

            fileInputStream = new FileInputStream(excelPath);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);

            XSSFDrawing patriarch = wb.getSheetAt(0).createDrawingPatriarch();
            XSSFClientAnchor anchor = new XSSFClientAnchor(params1, params2, params3, params4, params5, params6, params7, params8);
            anchor.setAnchorType(DONT_MOVE_AND_RESIZE);
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), XSSFWorkbook.PICTURE_TYPE_JPEG));

            fileOut = new FileOutputStream(excelPath);
            wb.write(fileOut);
        } catch (Exception e) {
            throw new ServiceException("add signature images failed. err: " + e.getMessage());
        } finally {
            IOUtils.closeQuietly(fileOut);
            IOUtils.closeQuietly(byteArrayOut);
            IOUtils.closeQuietly(fileInputStream);
        }
    }

    /**
     * 将 excel 文件转为 PDF
     *
     * @param excelPath 需要转换的 excel 文件存放路径
     * @param pdfPath   转换之后的 PDF 文件存放路径
     */
    public static void excel2PDF(String url, String excelPath, String pdfPath, RestTemplate restTemplate) {

        // 创建 Excel 文件对象
        File excelFile = new File(excelPath);
        if (!excelFile.exists()) {
            throw new ServiceException("Excel file does not exist: " + excelPath);
        }

        // 设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // 创建请求体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("fileInput", new FileSystemResource(excelFile));

        // 创建 HttpEntity
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // 发送 POST 请求
        ResponseEntity<byte[]> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, byte[].class);

        // 解析结果
        if (response.getStatusCode().is2xxSuccessful()) {
            // 如果请求成功
            byte[] pdfBytes = response.getBody();
            if (pdfBytes != null) {
                // 将 PDF 保存到指定路径
                try (FileOutputStream fos = new FileOutputStream(pdfPath)) {
                    fos.write(pdfBytes);
                } catch (IOException e) {
                    throw new ServiceException("save pdf file failed!, e: " + e.getMessage());
                }
            } else {
               throw new ServiceException("No content received in response.");
            }
        } else {
            throw new ServiceException("Conversion failed: " + response.getStatusCode());
        }
    }

}
