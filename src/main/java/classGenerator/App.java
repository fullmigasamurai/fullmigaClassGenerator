package classGenerator;

// import view.TelaInicial;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        // TelaInicial tela = new TelaInicial();
		// tela.setVisible(true);
		
		ArqSelector a = new ArqSelector();
		a.listFolder(new java.io.File("./tmp/ClasseMaiuscula"));
		FuFile f = new FuFile("./tmp/ClasseMaiuscula");
		
    }
}
