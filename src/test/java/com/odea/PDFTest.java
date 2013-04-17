package com.odea;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.junit.Test;

public class PDFTest {
    @Test
    public void pdfTest() throws IOException{
    	PDDocument doc = null;
        try
        {
            doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            
            PDXObjectImage ximage = new PDJpeg(doc, new FileInputStream("/home/gmazzei/logo2.jpg"));
            PDPageContentStream content = new PDPageContentStream(doc, page);
            content.drawImage(ximage, 20, 700 );
      
            PDFont font = PDType1Font.HELVETICA_BOLD;
            content.beginText();
            content.setFont(font, 9);
            content.moveTextPositionByAmount(400, 720);
            content.drawString("Este es un ejemplo de creacion de reportes");
            content.endText();
            
            content.beginText();
            content.setFont(font, 9);
            content.moveTextPositionByAmount(60, 35);
            content.drawString("Pagina numero 1 - Reporte");
            content.endText();
         
            content.beginText();
            content.setFont(font, 9);
            content.moveTextPositionByAmount(450, 35);
            content.drawString("www.odeasrl.com.ar");
            content.endText();

            String[][] tabla = {{"Este es un ejemplo de creacion de tablas","411"},
            					{"Pablo Bergonzi","47"},
                    			{"Pablo Nahuel Gotelli","95"},
                    			{"Gabriel Leandro Mazzei","195"},
                    			{"Pablo Paggi","421"},
                    			{"Michel Desplats","5"},
                    			{"ODEA SRL","1"},
                    			{"Renglon 3","64"},
                    			{"Renglon 1","888"},
                    			{"Renglon 2","546"},
                    			{"Renglon 3","6"},};
            
            drawTable(page, content, 680, 40, tabla);
                       

            content.close();
            
            doc.save("/home/gmazzei/PDFWithText.pdf");
        } catch (Exception e){
            System.out.println(e);
        } finally {
            if( doc != null )
            {
                doc.close();
            }
        }
    }

    
    public void drawTable(PDPage page, PDPageContentStream contentStream, float y, float margin, String[][] content) throws IOException  {

    	int lines = content.length;
    	float rowHeight = 20f;
        float tableWidth = page.findMediaBox().getWidth() - 2*margin;
        float tableHeight = rowHeight * lines;
        float firstColWidth = (float) (tableWidth*0.8);
        float secondColWidth = (float) (tableWidth*0.2);
        float cellMargin1 = 10f;
        float cellMargin2 = (secondColWidth/2);
        
        //Dibujamos las filas
        float nexty = y;
        contentStream.drawLine(margin, nexty, margin+tableWidth, nexty);
        nexty-= tableHeight;
        contentStream.drawLine(margin, nexty, margin+tableWidth, nexty);

        //Dibujamos las columnas
        float nextx = margin;
        contentStream.drawLine(nextx, y, nextx, y-tableHeight);
        nextx += firstColWidth;
        contentStream.drawLine(nextx, y, nextx, y-tableHeight);
        nextx += secondColWidth;
        contentStream.drawLine(nextx, y, nextx, y-tableHeight);
        
        
        //Agregamos el texto
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        float textx;
        float texty = y-15;
        
        for(int i = 0; i < lines; i++){
        	
        	String text = content[i][0];
        	textx = margin + cellMargin1;
	        contentStream.beginText();
	        contentStream.moveTextPositionByAmount(textx, texty);
	        contentStream.drawString(text);
	        contentStream.endText();
 
	        text = content[i][1];
	        textx = margin + firstColWidth + cellMargin2 - text.length()*6;
	        contentStream.beginText();
	        contentStream.moveTextPositionByAmount(textx, texty);
	        contentStream.drawString(text);
	        contentStream.endText();
        	
            texty-=rowHeight;
        }
        
    	
    }
    
    
    @Test
    public void tablaPDF() throws COSVisitorException, IOException {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);

        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        String[][] content = {{"a","b", "1"},
                {"c","d", "2"},
                {"e","f", "3"},
                {"g","h", "4"},
                {"i","j", "5"}} ;

        drawTable2(page, contentStream, 700, 100, content);
        contentStream.close();
        doc.save("/home/gmazzei/Tabla.pdf");
    }
    
    
    /**
     * @param page
     * @param contentStream
     * @param y the y-coordinate of the first row
     * @param margin the padding on left and right of table
     * @param content a 2d array containing the table data
     * @throws IOException
     */
    public static void drawTable2(PDPage page, PDPageContentStream contentStream, float y, float margin, String[][] content) throws IOException {
        final int rows = content.length;
        final int cols = content[0].length;
        final float rowHeight = 20f;
        final float tableWidth = page.findMediaBox().getWidth() - margin - margin;
        final float tableHeight = rowHeight * rows;
        final float colWidth = tableWidth/(float)cols;
        final float cellMargin=5f;

        //draw the rows
        float nexty = y;
        for (int i = 0; i <= rows; i++) {
            contentStream.drawLine(margin, nexty, margin+tableWidth, nexty);
            nexty-= rowHeight;
        }

        //draw the columns
        float nextx = margin;
        for (int i = 0; i <= cols; i++) {
            contentStream.drawLine(nextx, y, nextx, y-tableHeight);
            nextx += colWidth;
        }

        //now add the text
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

        float textx = margin+cellMargin;
        float texty = y-15;
        for(int i = 0; i < content.length; i++){
            for(int j = 0 ; j < content[i].length; j++){
                String text = content[i][j];
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(textx,texty);
                contentStream.drawString(text);
                contentStream.endText();
                textx += colWidth;
            }
            texty-=rowHeight;
            textx = margin+cellMargin;
        }
    }
    
    
}