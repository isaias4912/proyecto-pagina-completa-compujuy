package ar.com.jsuper.mapper;

import ar.com.jsuper.domain.*;
import ar.com.jsuper.dto.*;
import ar.com.jsuper.utils.ArrayUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.awt.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    @Mapping(source = "imagenProducto", target = "imagen")
    ProductoListDTO productoToProductDto(Producto producto);

    @Mapping(source = "imagen", target = "imagenProducto")
    Producto productoDtoToProduct(ProductoListDTO productoListDTO);

    ProductoPadreSinProductoDTO productoPadreToProductoPadreDto(ProductoPadre producto);

    ProductoPadre productoPadreDtoToProductoPadre(ProductoPadreSinProductoDTO productoListDTO);

    CodigoBarrasDTO codBarraToCodigoBarraDto(CodigoBarras codigoBarras);

    CodigoBarras codBarraDtoToCodigoBarra(CodigoBarrasDTO codigoBarras);

    ProductosCompuestosDTO productoCompuestoToProductoCompuestoDto(ProductosCompuestos producto);

    ProductosCompuestos productoCompuestoDtoToProductoCompuesto(ProductosCompuestosDTO productoListDTO);

    ListaPreciosDTO listaPrecioToListaPrecioDto(ListaPrecios producto);

    ListaPrecios listaPrecioDtoToListaPrecio(ListaPreciosDTO productoListDTO);

    ImagenProductoDTO imagenProductoToImagenProductoDto(ImagenProducto producto);

    ImagenProducto imagenProductoDtoToImagenProducto(ImagenProductoDTO productoListDTO);

    List<ProductoListDTO> productoListToProductListDto(List<Producto> productos);

    default ImagenProductoDTO map(Set<ImagenProducto> value) {
        try {
            if (Objects.nonNull(value) && !value.isEmpty()) {
                ImagenProducto imagenProducto = value.stream().filter(x -> x.getOrden().equals(1)).collect(ArrayUtil.toSingleton());
                return this.imagenProductoToImagenProductoDto(imagenProducto);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    default Set<ImagenProducto> map(ImagenProductoDTO value) {
        try {
            if (Objects.nonNull(value)) {
                return Collections.singleton(this.imagenProductoDtoToImagenProducto(value));
            }
        } catch (Exception e) {
        }
        return null;
    }
}
