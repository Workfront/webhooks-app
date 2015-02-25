package webhooks.core.services.util;

import org.apache.pdfbox.pdfviewer.*;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.*;
import org.apache.tika.io.*;
import webhooks.core.models.entities.*;

import javax.servlet.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Utility functions to create different thumbnails based on the file types
 */
public class ThumbnailUtil {

	public static BufferedImage createThumbnail(ServletContext context, String filename, Document document, int thumbWidth, int thumbHeight) throws IOException, InterruptedException {
		BufferedImage thumbImage = null;
		String mimeType = document.getMimeType();
		if (mimeType != null) {
			if (mimeType.contains("image")) {
				//thumbImage = Thumbnails.of(new File(filename)).size(thumbWidth, thumbHeight).asBufferedImage();
				thumbImage = createImageThumbnail(filename, thumbWidth, thumbHeight);
			} else if (mimeType.contains("pdf")){
				thumbImage = createPDFThumbnail (filename, thumbWidth, thumbHeight);
			} else if (mimeType.contains("text")){
				String fromStream = IOUtils.toString(new FileInputStream(filename), "UTF-8");
				String allowStr = (fromStream.length() > 500) ? fromStream.substring(0, 500) : fromStream;
				thumbImage = createTextThumbnail(allowStr, thumbWidth, thumbHeight);
			} else {
				// generic file thumbnail
				String realPath = context.getRealPath("/WEB-INF/static/generic-icon.png");
				thumbImage = createImageThumbnail(realPath, thumbWidth, thumbHeight);
			}
		} else {
			// generic folder thumbnail
			String realPath = context.getRealPath("/WEB-INF/static/folder.png");
			thumbImage = createImageThumbnail(realPath, thumbWidth, thumbHeight);
		}

		return thumbImage;
	}

	private static BufferedImage createPDFThumbnail (String filename, int thumbWidth, int thumbHeight) throws IOException {
		PDDocument document = PDDocument.load(new File(filename));
		List<?> pages = document.getDocumentCatalog().getAllPages();

		// get first page
		PDPage page = (PDPage)pages.get(0);

		PDRectangle mBox = page.findMediaBox();
		float scaling = thumbWidth / mBox.getWidth();

		Dimension pageDimension = new Dimension((int) mBox.getWidth(), (int) mBox.getHeight());

		BufferedImage thumbImg = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = (Graphics2D)thumbImg.getGraphics();
		graphics.setBackground(new Color(255, 255, 255, 0)); // TRANSPARENT_WHITE
		graphics.clearRect(0, 0, thumbImg.getWidth(), thumbImg.getHeight());
		graphics.scale(scaling, scaling);
		PageDrawer drawer = new PageDrawer();
		drawer.drawPage(graphics, page, pageDimension);

		return thumbImg;
    }


	private static BufferedImage createImageThumbnail(String filename, int thumbWidth, int thumbHeight)
		throws InterruptedException, IOException
	{
		// load image from filename
		Image image = Toolkit.getDefaultToolkit().getImage(filename);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image, 0);
		mediaTracker.waitForID(0);

		// determine thumbnail size from WIDTH and HEIGHT
		double thumbRatio = (double)thumbWidth / (double)thumbHeight;
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);
		double imageRatio = (double)imageWidth / (double)imageHeight;
		if (thumbRatio < imageRatio) {
			thumbHeight = (int)(thumbWidth / imageRatio);
		} else {
			thumbWidth = (int)(thumbHeight * imageRatio);
		}

		// draw original image to thumbnail image object and
		// scale it to the new size on-the-fly
		BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics2D = thumbImage.createGraphics();

		graphics2D.setBackground(new Color(255, 255, 255, 0)); // TRANSPARENT_WHITE
		graphics2D.clearRect(0, 0, thumbImage.getWidth(), thumbImage.getHeight());
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
		graphics2D.dispose();

		return thumbImage;
	}

	private static BufferedImage createTextThumbnail(String text, int thumbWidth, int thumbHeight){
		/*
           Create a small, temporary image to ascertain the width and height of the final image
         */
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = img.createGraphics();
		Font font = new Font("Arial", Font.BOLD, 10);
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();

		StringTokenizer tok = new StringTokenizer(text, "\n");
		int maxLength = 0;
		String maxStr = null;
		ArrayList<String> strings = new ArrayList<String>();
		int count = 0;
		while (tok.hasMoreTokens()) {
			String str = tok.nextToken();
			int len = str.length();
			strings.add(str);
			if (len > maxLength)
			{
				maxLength = len;
				maxStr = str;
			}
			count++;
		}

		int width = fm.stringWidth(maxStr);
		int height = fm.getHeight() * count;

		g2d.dispose();

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = img.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2d.setFont(font);
		fm = g2d.getFontMetrics();
		g2d.setBackground(new Color(255, 255, 255, 0)); // TRANSPARENT_WHITE
		g2d.clearRect(0, 0, img.getWidth(), img.getHeight());
		int y = 0;
		for (String str : strings) {
			y += fm.getAscent();
			g2d.drawString(str, 0, y);
		}
		g2d.dispose();

		// resize
		Image tmp = img.getScaledInstance(thumbWidth, thumbHeight, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_ARGB);

		g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();

		//img = Scalr.resize(img, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, thumbWidth, thumbHeight, Scalr.OP_ANTIALIAS);

		return dimg;
	}
}
