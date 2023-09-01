package ar.com.jsuper.main;

//import java.util.Date;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.hibernate.annotations.Filter;
//import org.hibernate.criterion.Projections;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import ar.com.jsuper.dao.exception.BussinessException;
//import ar.com.jsuper.dao.utils.FamiliasUtil;
//import ar.com.jsuper.domain.CodigoBarras;
//import ar.com.jsuper.domain.Familias;
//import ar.com.jsuper.domain.PrecioProductos;
//import ar.com.jsuper.domain.Producto;
//import ar.com.jsuper.domain.ProductoPadre;
//import ar.com.jsuper.domain.Roles;
//import ar.com.jsuper.domain.Rubros;
//import ar.com.jsuper.domain.StockSucursal;
//import ar.com.jsuper.domain.TipoPrecios;
//import ar.com.jsuper.domain.UbicacionStock;
//import ar.com.jsuper.domain.Usuarios;
//import ar.com.jsuper.dto.ProductoDTO;
//import ar.com.jsuper.dto.FamiliaDTO;
//import ar.com.jsuper.dto.ProductoPadreDTO;
//import ar.com.jsuper.dto.RubrosDTO;
//import ar.com.jsuper.dto.StockSucursalDTO;
//import ar.com.jsuper.dto.SucursalesDTO;
//import ar.com.jsuper.dto.UnidadDTO;
//import ar.com.jsuper.security.SimpleEncryption;
//import ar.com.jsuper.services.FamiliasService;
//import ar.com.jsuper.services.PrecioProductoService;
//import ar.com.jsuper.services.ProductosService;
//import ar.com.jsuper.services.RubrosService;
//import ar.com.jsuper.services.SecurityService;
//import ar.com.jsuper.services.TipoPreciosService;
//import ar.com.jsuper.services.UnidadService;
//import ar.com.jsuper.services.UsuariosService;
//import ar.com.jsuper.services.impl.FamiliasServiceImpl;
//import java.math.BigDecimal;
//import java.security.MessageDigest;
//import java.util.ArrayList;
//import javax.xml.bind.DatatypeConverter;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;

public class Principal {

//    @Autowired
//    protected static SessionFactory sessionFactory;
//    @Autowired
//    protected static PasswordEncoder encoder;

    public static void main(String[] args) throws Exception {
//        System.out.println("estamso aqui 8888-------------------------");
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
//        RubrosService rubroService = (RubrosService) ctx.getBean("rubrosServiceImpl");
//        ProductosService prodService = (ProductosService) ctx.getBean("productosServiceImpl");
//        SecurityService<Familias> sec = (SecurityService) ctx.getBean("securityServiceImpl");
//        sec.checkIfIdBelognsApp(2);

//        TipoPreciosService tipoPrecioService = (TipoPreciosService) ctx.getBean("tipoPreciosServiceImpl");
//        PrecioProductoService precioProductoService = (PrecioProductoService) ctx.getBean("precioProductosServiceImpl");
//        FamiliasService familiasService = (FamiliasService) ctx.getBean("familiasServiceImpl");
//        Familias f= familiasService.getFamilia(1);
//        System.out.println("estamso aqui-------------------------");
//        UsuariosService usuariosService = (UsuariosService) ctx.getBean("usuariosServiceImpl");
//        Usuarios u = usuariosService.getByUser("user");
////        Set<Roles> roles = usuariosService.getRolesByUser(u);
//        System.out.println("roles int:" + u.getRoles().size());
//        Familias f= familiasService.getFamilia(1);
//          Session session = sessionFactory.getCurrentSession();
//          StSessockSucursal stockSucursal= new StockSucursal();
//          stockSucursal.setDetalle("ssssssssssss");
//          stockSucursal.setStock(BigDecimal.ONE);
//          stockSucursal.setStockMinimo(BigDecimal.ONE);
//          session.save(stockSucursal);ni
//          session.close();
//              MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        System.out.println("leyendo el test-------------------------");
//          boolean t=converter.canRead(ProductoPadreDTO.class, MediaType.APPLICATION_JSON);
//          boolean t1=converter.canWrite(ProductoPadreDTO.class, MediaType.APPLICATION_JSON);
//          System.out.println("resultado:"+t);
//          System.out.println("resultado  write:"+t1);
///////////////////////###########################encriptacion
//        System.out.println("encriptando - 3");
//        String v = SimpleEncryption.encrypt("CompujuyJSuper22", "JSuperJSuper1234", "3");
//        System.out.println("-----" + v);
//        System.out.println("-----8:" + SimpleEncryption.decrypt("CompujuyJSuper22", "JSuperJSuper1234", v));
//
//        org.springframework.security.authentication.encoding.PasswordEncoder x = new Md5PasswordEncoder();
//        String password = "rafa2299@gmail.com";
//
//        MessageDigest md = MessageDigest.getInstance("MD5");
//        md.update(password.getBytes());
//        byte[] digest = md.digest();
//        String myHash = DatatypeConverter
//                .printHexBinary(digest).toLowerCase();
//        System.out.println("encoder mail:" + myHash);
//#######################################fin de encriptacion###########################        
//          UbicacionStock ubicacionStock= new UbicacionStock();
//          ubicacionStock.setCantidad(new BigDecimal("10.2"));
//          ubicacionStock.setUbicacion(null);
//          ProductosDTO pdto= new ProductosDTO();
//          pdto.setNombre("un prodcto");
//          pdto.setContenidoNeto(new BigDecimal("2.36"));
//          DetalleProductosDTO dpdto= new DetalleProductosDTO();
//          dpdto.setDetalle("ssssssss");;
//          Set<DetalleProductosDTO> l=  new HashSet<DetalleProductosDTO>();
//          RubrosDTO r= new RubrosDTO();
//          r.setId(2);
//          
//          FamiliaDTO fdto = new FamiliaDTO();
//          fdto.setId(2);
//          pdto.setDetalleProductos(l);
//          pdto.setRubro(r);
//          pdto.setFamilia(fdto);
//          
//          prodService.insert(pdto);
//        System.out.println("familias:"+familiasService.getIdOfChildsByFamilia(f));
        // Rubros rubros= new Rubros();
        //// rubros.setNombre("Electricidad 4444");
        //// rubros.setDescripcion("Descripcion Electricidad 78787");
        ////
        //// rubroService.insertRubro(rubros);
        // rubros= rubroService.getRubro(2);
        //
        // Productos p= new Productos();
        // p.setActivo(true);
        // p.setCodigoBarra("12435435");
        // p.setContenidoNeto(new BigDecimal("11.5"));
        // p.setDetalle("detalle");
        // p.setIva(new BigDecimal("21.5"));
        // p.setNombre("Fanta naranja 350-44");
        // p.setRubro(rubros);
        // p.setUnidad("Metro");
        //
        // Set<CodigoBarras> listaCodigos= new HashSet<>();
        // CodigoBarras c= new CodigoBarras();
        // c.setCodigo("12121212121");
        // c.setDescripcion("Descripcion codigo");
        // c.setNombre("nombre de cb");
        // c.setProducto(p);
        // listaCodigos.add(c);
        // c= new CodigoBarras();
        // c.setCodigo("45454545454545");
        // c.setDescripcion("Descripcion codigo");
        // c.setNombre("nombre de cb");
        // c.setProducto(p);
        // listaCodigos.add(c);
        // p.setCodigoBarras(listaCodigos);
        //
        // // lista de precios
        // TipoPrecios tipoPrecios= new TipoPrecios();
        //// tipoPrecios.setActivo(true);
        //// tipoPrecios.setDesde(new Date());;
        //// tipoPrecios.setHasta(new Date());;
        ////
        //// tipoPrecioService.insert(tipoPrecios);
        // tipoPrecios=tipoPrecioService.get(1);
        // Set<PrecioProductos> precioProductos= new HashSet<>();
        // PrecioProductos pp= new PrecioProductos();
        // pp.setActivo(true);
        // pp.setProducto(p);
        // pp.setTipoPrecio(tipoPrecios);
        // pp.setPrecioTarjetaCred(new BigDecimal("20.25"));
        // precioProductos.add(pp);
        // p.setPrecios(precioProductos);
        //
        //// Precios pr= new Precios();
        //// pr.set
        //
        // prodService.insert(p);
//		Set<Productos> listap = prodService.getall();
//		for (Productos productos : listap) {
//			System.out.println("------prod:" + productos.getNombre());
//			System.out.println("------prod id:" + productos.getId());
//			System.out.println("------prod codigos:" + productos.getCodigoBarras());
//			System.out.println("------prod rubro:" + productos.getRubro());
//			if (productos.getFamilia() != null){
//				System.out.println("------prod familia:" + productos.getFamilia().getNombre());
//				if (productos.getFamilia().getFamilia() != null){
//					System.out.println("------prod familia:" + productos.getFamilia().getFamilia().getNombre());
//				}
//			}
//
//		}
//		Productos p= prodService.getForPublicView(1);
//        precioProductoService.getAll();
//        familiasService.getAllByShortName();
//        familiasService.getall();
//        prodService.getall();
//		System.out.println("--------------------------sssss--------------------"+p.getNombre());
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
//        ProductosService productosService = (ProductosService) ctx.getBean("productosServiceImpl");
//        productosService.prueba();
       

    }

}
