package ar.com.jsuper.dao;

import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.ImagenProducto;
import ar.com.jsuper.domain.Producto;
import java.util.Set;

public interface ImagenProductoDAO extends GenericDAO<ImagenProducto, Integer> {

	void saveUpdateOrDelete(Producto producto, Set<ImagenProducto> imagenProductos, Set<ImagenProducto> imagenProductosBD) throws BussinessException;

	ImagenProducto getObject(ImagenProducto imagenProductoOld, ImagenProducto imagenProductoNew);

	ImagenProducto getFirtsImageFromIdProducto(Integer idProducto);

}
