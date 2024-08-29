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
import javax.swing.*;

public class WeatherAppGUI extends JFrame {

    public WeatherAppGUI() {
        super("Weather App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 650);

        // place the GUI at the center of the screen
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
    }
}
