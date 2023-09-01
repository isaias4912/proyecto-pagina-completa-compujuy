/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.utils;

import ar.com.jsuper.services.factElec.ws.ArrayOfErr;
import ar.com.jsuper.services.factElec.ws.Err;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author rafa
 */
public class AfipUtil {

	public AfipUtil() {
	}

	public static Set<Error> getErrorFromErrorAfip(ArrayOfErr arrayOfErr) {
		if (Objects.nonNull(arrayOfErr)) {
			List<Err> err = arrayOfErr.getErr();
			if (Objects.nonNull(err)) {
				if (err.size() > 0) {
					Set<Error> errors = new HashSet<>();
					for (Err er : err) {
						Error e = new Error();
						e.setCode(er.getCode());
						e.setMessage(er.getMsg());
						errors.add(e);
					}
					return errors;
				}
			}
		}
		return null;
	}

	public final static String getDescripcionFromId(Integer id) {
		String descripcion;
		switch (id) {
			case 3:
				descripcion = "0%";
				break;
			case 4:
				descripcion = "10.5%";
				break;
			case 5:
				descripcion = "21%";
				break;
			case 6:
				descripcion = "27%";
				break;
			case 8:
				descripcion = "5%";
				break;
			case 9:
				descripcion = "2.5%";
				break;
			default:
				descripcion = "0%";
				break;
		}
		return descripcion;
	}

	public final static Double getAlicuotaFromId(Integer id) {
		Double alicuota;
		switch (id) {
			case 3:
				alicuota = new Double("0");
				break;
			case 4:
				alicuota = new Double("10.5");
				break;
			case 5:
				alicuota = new Double("21");
				break;
			case 6:
				alicuota = new Double("27");
				break;
			case 8:
				alicuota = new Double("5");
				break;
			case 9:
				alicuota = new Double("2.5");
				break;
			default:
				alicuota = new Double("0");
				break;
		}
		return alicuota;
	}
}
