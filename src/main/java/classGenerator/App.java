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
		
		ArqSelector a = new ArqSelector("./tmp");
		// a.ListPath();
		// a.listFolder(new java.io.File("./tmp"));
		a.listPath(new java.io.File("./tmp"));
    }
}
