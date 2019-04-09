package br.com.dasa.print.core.redis.model;

/**
 * Informacoes sobre tipos de etiqueta.
 *
 * Contem informacoes predefinidas de largura e altura em milimetros das etiquetas.
 *
 */
public enum TipoEtiqueta {
    PORTRAIT(31, 55, 3),
    LANDSCAPE(45, 30, 3);

    double width;
    double height;
    double gap;

    TipoEtiqueta(double width, double height, double gap) {
        this.width = width;
        this.height = height;
        this.gap = gap;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getGap() {
        return gap;
    }
}
