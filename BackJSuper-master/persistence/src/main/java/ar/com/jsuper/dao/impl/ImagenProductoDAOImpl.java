package ar.com.jsuper.dao.impl;

import ar.com.jsuper.dao.ImagenProductoDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.domain.ImagenProducto;
import ar.com.jsuper.domain.Producto;
import java.util.Objects;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ImagenProductoDAOImpl extends GenericDAOImpl<ImagenProducto, Integer> implements ImagenProductoDAO {

	public void saveUpdateOrDelete(Producto producto, Set<ImagenProducto> imagenProductos, Set<ImagenProducto> imagenProductosBD) throws BussinessException {
		Session session = sessionFactory.getCurrentSession();
		Boolean encontro = false;
		ImagenProducto imgTemp = null;
		for (ImagenProducto img : imagenProductos) {
			encontro = false;
			for (ImagenProducto imgBD : imagenProductosBD) {
				if (Objects.equals(img.getId(), imgBD.getId())) {
					encontro = true;
					imgTemp = this.getObject(imgBD, img);
				}
			}
			if (encontro) {
				session.update(imgTemp);
			} else {
				img.setProducto(producto);
				session.save(img);
			}
		}
		for (ImagenProducto imgBD : imagenProductosBD) {
			encontro = false;
			for (ImagenProducto img : imagenProductos) {
				if (Objects.equals(img.getId(), imgBD.getId())) {
					encontro = true;
				}
			}
			if (!encontro) {
				session.delete(imgBD);
			}
		}
	}

	@Override
	public ImagenProducto getObject(ImagenProducto imagenProductoOld, ImagenProducto imagenProductoNew) {
		if (Objects.isNull(imagenProductoOld)) {
			imagenProductoOld = new ImagenProducto();
		}
		imagenProductoOld.setNombre(imagenProductoNew.getNombre());
		imagenProductoOld.setPath(imagenProductoNew.getPath());
		imagenProductoOld.setTag(imagenProductoNew.getTag());
		imagenProductoOld.setTamanio(imagenProductoNew.getTamanio());
		imagenProductoOld.setOrden(imagenProductoNew.getOrden());
		imagenProductoOld.setTipo(imagenProductoNew.getTipo());
		return imagenProductoOld;
	}

	@Override
	public ImagenProducto getFirtsImageFromIdProducto(Integer idProducto) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from ImagenProducto where producto.id= :idProducto order by orden ASC");
		query.setParameter("idProducto", idProducto);
		query.setMaxResults(1);
		ImagenProducto imagen = (ImagenProducto) query.uniqueResult();
		return imagen;
	}
}
