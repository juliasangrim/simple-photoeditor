import java.awt.image.BufferedImage;

public interface IFilter {
    BufferedImage filteredImage(BufferedImage image);
}
