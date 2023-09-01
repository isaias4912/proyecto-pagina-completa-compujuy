package ar.com.jsuper.dao.utils;


import ar.com.jsuper.domain.Familias;

public class FamiliasUtil {

    public FamiliasUtil() {

    }

    public static String getIdsFamilias(Familias familia) {
        String nombreCorto = "";
        if (familia.getFamilia() != null) {
            nombreCorto = String.valueOf(familia.getId()) + "-" + getIdsFamilias(familia.getFamilia());
        } else {
            nombreCorto = String.valueOf(familia.getId());
        }
        return nombreCorto;
    }

    public static String getIdOfChildsByFamilia(Familias familia) {
        String nombreCorto = "";
        if (familia.getFamilias() != null) {
            if (familia.getFamilias().size() > 0) {
                nombreCorto = String.valueOf(familia.getId());
                for (Familias fam : familia.getFamilias()) {
                    nombreCorto += "-" + getIdOfChildsByFamilia(fam);
                }
            } else {
                nombreCorto = String.valueOf(familia.getId());
            }
        } else {
            nombreCorto = String.valueOf(familia.getId());
        }
        return nombreCorto;
    }
    // private String getPathShortName(Familias familia) {
    // String nombreCorto = "";
    // if (familia.getFamilia() != null) {
    // nombreCorto = familia.getNombreCorto() + "-" +
    // getPathShortName(familia.getFamilia());
    // } else {
    // nombreCorto = familia.getNombreCorto();
    // }
    // return nombreCorto;
    // }
}
