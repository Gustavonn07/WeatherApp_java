/*
    javax.swing é um pacote na biblioteca padrão do Java que fornece uma série
    de classes para construir interfaces gráficas de usuário (GUIs).
    Ele é parte do Java Foundation Classes (JFC), que também inclui AWT (Abstract Window Toolkit) e
    outras ferramentas para criar interfaces gráficas.

    JFrame: Representa uma janela principal em uma aplicação.
    É a classe base para criar uma janela com título, borda e botões de controle.

    JPanel: Um contêiner que pode ser usado para organizar outros componentes na interface gráfica.

    JButton: Um botão que pode ser clicado pelo usuário para realizar uma ação.

    JLabel: Um componente que exibe texto ou ícones.

    JTextField: Um campo de texto onde o usuário pode inserir uma linha de texto.

    JTextArea: Uma área de texto onde o usuário pode inserir múltiplas linhas de texto.

    JComboBox: Um componente de menu suspenso que permite ao usuário selecionar um item de uma lista.
 */
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame {

    public WeatherAppGUI() {
        super("Weather App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 650);

        // place the GUI at the center of the screen
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        addGuiComponents();
    }

    private void addGuiComponents() {
        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(15, 15, 301, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(325, 13, 47, 45);
        add(searchButton);

        JLabel weatherConditionImage = new JLabel(loadImage("src/assets/cloudy.png"));
        weatherConditionImage.setBounds(-35, 125, 450, 217);
        add(weatherConditionImage);

        JLabel temperatureText = new JLabel("10 C");
        temperatureText.setBounds(-35, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(-35, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(0, 500, 74, 66);
        add(humidityImage);

        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityText.setBounds(65, 505, 85, 55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        JLabel windspeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windspeedImage.setBounds(215, 500, 74, 66);
        add(windspeedImage);

        JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h</html>");
        windspeedText.setBounds(295, 505, 85, 55);
        windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedText);
    }

    private ImageIcon loadImage(String path) {
        try {
            // read the image file form the path given
            BufferedImage image = ImageIO.read(new File(path));
            return new ImageIcon(image);

        } catch (IOException err) {
            err.printStackTrace();
        }

        System.out.println("Could not find resource");
        return null;
    }
}
