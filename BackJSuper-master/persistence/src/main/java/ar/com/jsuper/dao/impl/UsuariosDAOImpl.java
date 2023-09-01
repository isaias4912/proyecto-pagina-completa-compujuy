package ar.com.jsuper.dao.impl;

import java.util.HashSet;
import java.util.Set;

import ar.com.jsuper.services.impl.BaseService;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import ar.com.jsuper.dao.UsuariosDAO;
import ar.com.jsuper.dao.exception.BussinessException;
import ar.com.jsuper.dao.utils.Pagination;
import ar.com.jsuper.domain.App;
import ar.com.jsuper.domain.Claves;
import ar.com.jsuper.domain.Cliente;
import ar.com.jsuper.domain.CodigoBarras;
import ar.com.jsuper.domain.Configuracion;
import ar.com.jsuper.domain.Empresas;
import ar.com.jsuper.domain.Entidad;
import ar.com.jsuper.domain.Familias;
import ar.com.jsuper.domain.LogUser;
import ar.com.jsuper.domain.Personas;
import ar.com.jsuper.domain.Producto;
import ar.com.jsuper.domain.ProductoPadre;
import ar.com.jsuper.domain.Proveedores;
import ar.com.jsuper.domain.RecuperacionPass;
import ar.com.jsuper.domain.Roles;
import ar.com.jsuper.domain.Sucursales;
import ar.com.jsuper.domain.Unidad;
import ar.com.jsuper.domain.Usuarios;
import ar.com.jsuper.domain.utils.FilterUsuarios;
import ar.com.jsuper.security.SimpleEncryption;
import ar.com.jsuper.security.TenantContext;
import ar.com.jsuper.utils.Constants;
import ar.com.jsuper.utils.Doc;
import ar.com.jsuper.utils.TipoCliente;
import ar.com.jsuper.utils.TipoEmpresa;
import ar.com.jsuper.utils.TipoEntidad;
import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Value;

@Repository
public class UsuariosDAOImpl extends GenericDAOImpl<Usuarios, Integer> implements UsuariosDAO {

    private Logger logger = Logger.getLogger(UsuariosDAOImpl.class);
    @Value("${path.general}")
    private String pathGeneral;

    @Override
    public Usuarios getByUser(String user) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Usuarios.class, "usuario");
        c.setFetchMode("usuario.app", FetchMode.JOIN);
        c.createAlias("usuario.app", "app");
        c.setFetchMode("roles", FetchMode.JOIN);
        Criterion c1 = Restrictions.eq("usuario", user);
        Criterion c2 = Restrictions.eq("estado", true);
        c.add(c1);
        c.add(c2);
        return (Usuarios) c.uniqueResult();
    }

    @Override
    public List<Usuarios> getByUserMatch(String name) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Usuarios.class, "usuario");
        Criteria criteriaPersonas = c.createCriteria("usuario.persona", "persona", JoinType.LEFT_OUTER_JOIN);
        Criteria criteriaApp = c.createCriteria("usuario.app", "app", JoinType.LEFT_OUTER_JOIN);
        /*###########################Control por APP############################*/
        Criterion ca = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        c.add(ca);
        /*######################################################################*/
        Criterion c1 = Restrictions.like("usuario", name, MatchMode.ANYWHERE);
        Criterion c2 = Restrictions.like("persona.apellido", name, MatchMode.ANYWHERE);
        Criterion c3 = Restrictions.like("persona.nombre", name, MatchMode.ANYWHERE);
        c.add(Restrictions.or(c1, c2, c3));
        c.add(Restrictions.eq("estado", true));
        return c.list();
    }

    @Override
    public Usuarios getUserById(Integer idUser) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Usuarios.class, "usuario");
        Criteria criteriaPersonas = c.createCriteria("usuario.persona", "persona", JoinType.LEFT_OUTER_JOIN);
//        Criteria criteriaApp = c.createCriteria("usuario.app", "app", JoinType.LEFT_OUTER_JOIN);
        /*###########################Control por APP############################*/
//        Criterion ca = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
//        c.add(ca);
        /*######################################################################*/
        Criterion c1 = Restrictions.eq("id", idUser);
        c.add(c1);
        c.add(Restrictions.eq("estado", true));
        return (Usuarios) c.uniqueResult();
    }

    @Override
    public Usuarios getUserByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Usuarios.class, "usuario");
        Criteria criteriaPersonas = c.createCriteria("usuario.persona", "persona", JoinType.LEFT_OUTER_JOIN);
        Criterion c1 = Restrictions.eq("mail", email);
        c.add(c1);
        c.add(Restrictions.eq("estado", true));
        try {
            return (Usuarios) c.uniqueResult();
        } catch (HibernateException he) {
            logger.error("Error al traer usuario por email, no existe o hubo otro error", he);
            return null;
        }
    }

    @Override
    public Usuarios getByIdFacebook(String idUserFaceboook) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Usuarios.class, "usuario");
        c.setFetchMode("roles", FetchMode.JOIN);
        c.setFetchMode("app", FetchMode.JOIN);
        Criteria criteriaPersonas = c.createCriteria("usuario.persona", "persona", JoinType.LEFT_OUTER_JOIN);
        Criterion c1 = Restrictions.eq("idRedSocial", idUserFaceboook);
        Criterion c2 = Restrictions.eq("tipoUsuario", 2);// 2 es facebook
        c.add(c1);
        c.add(c2);
        c.add(Restrictions.eq("estado", true));
        try {
            return (Usuarios) c.uniqueResult();
        } catch (HibernateException he) {
            logger.error("Error  en usuarios de facebook", he);
            return null;
        }
    }

    @Override
    public Usuarios getByUserOrMail(String userOrMail) {
        Session session = sessionFactory.getCurrentSession();
//        String hqlUpdate = "select distinct u from Usuarios u  "
//                + "left join u.sucursales us "
//                + "left join u.roles ur "
//                + "left join u.app ua "
//                + "where u.estado=:estado "
//                + "and (u.usuario=:userOrMail or u.mail=:userOrMail)";
//        Usuarios usuario = (Usuarios) session.createQuery(hqlUpdate)
//                .setParameter("estado", true)
//                .setParameter("userOrMail", userOrMail)
//                .getSingleResult();
//        Criteria c = session.createCriteria(Usuarios.class, "usuario");
//        Criteria sucursales = c.createCriteria("usuario.sucursales", JoinType.LEFT_OUTER_JOIN);
//        Criteria roles = c.createCriteria("usuario.roles", JoinType.LEFT_OUTER_JOIN);
//        c.setFetchMode("usuario.app", FetchMode.JOIN);
////        c.createAlias("usuario.app", "app");
////        c.setFetchMode("roles", FetchMode.JOIN);
////        c.setFetchMode("sucursales", FetchMode.JOIN);
//        Criterion cUser = Restrictions.eq("usuario", userOrMail);
//        Criterion cMail = Restrictions.eq("mail", userOrMail);
//        Criterion cEstado = Restrictions.eq("estado", true);
//        c.add(Restrictions.disjunction(cUser, cMail));
//        c.add(cEstado);
//        c.setProjection(Projections.distinct(Projections.property("id")));

        String productosHQL = "SELECT DISTINCT u from Usuarios u "
                + " JOIN FETCH u.sucursales us"
                + " JOIN FETCH u.roles r"
                + " JOIN FETCH u.app ua"
                + " WHERE u.usuario= :userOrMail OR u.mail = :userOrMail"
                + " AND u.estado= :estado";
        Query query = session.createQuery(productosHQL)
                .setParameter("userOrMail", userOrMail)
                .setParameter("estado", true);
//        List results = query.uniqueResult();

        try {
            return (Usuarios) query.uniqueResult();
        } catch (HibernateException he) {
            return null;
        }
//return usuario;
    }

    @Override
    public List<Usuarios> getAllListActive() throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Usuarios.class, "usuario");
        /*###########################Control por APP############################*/
        Criterion c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        c.add(c1);
        /*######################################################################*/
        c1 = Restrictions.eq("estado", true);
        c.add(c1);
        return c.list();
    }

    @Override
    public Set<Roles> getRolesByUser(Usuarios user) {
        Session session = sessionFactory.getCurrentSession();
        Usuarios u = session.get(Usuarios.class, user.getId());
        return u.getRoles();
    }

    @Override
    public List<Roles> getRoles() {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Roles.class, "roles");
        return c.list();
    }

    @Override
    public boolean isExistUser(String user) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Usuarios.class, "usuario");
        Criterion c1 = Restrictions.eq("usuario", user);
        c.add(c1);
        try {
            return !Objects.isNull(c.uniqueResult());
        } catch (HibernateException e) {// en caso con resultado mas de una  o uno que no vale
            return true;
        }
    }

    @Override
    public boolean isExistMailUser(String mail) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Usuarios.class, "usuario");
        Criterion c1 = Restrictions.eq("mail", mail);
        c.add(c1);
        try {
            return !Objects.isNull(c.uniqueResult());
        } catch (HibernateException e) {// en caso con resultado mas de una  o uno que no vale
            return true;
        }
    }

    /**
     * Agrega un usuario desde la interfaz publica
     */
    @Override
    public Usuarios insert(Usuarios usuario) {
        Session session = sessionFactory.getCurrentSession();
        Configuracion configuracion = new Configuracion();
        configuracion.setCliCtaCteInteres(new BigDecimal("5"));
        configuracion.setIva(new BigDecimal("21.0"));
        configuracion.setMargenGanancia(new BigDecimal("25.0"));
        configuracion.setPais("ARGENTINA");
        configuracion.setEncabezadoReporte("<h1>" + usuario.getUsuario() + "</h1>");
        configuracion.setCutDescTicket(Boolean.TRUE);
        configuracion.setSizeDescTicket(4);
        configuracion.setEnabledVenta(Boolean.FALSE);
        configuracion.setAfipProduccion(Boolean.FALSE);
        configuracion.setTipoEmpresa(TipoEmpresa.MONOTRIBUTO); // por el momento le mandamos en mosnotributista[1]
//		configuracion.set
        session.save(configuracion);
        // agregamos una app con el nombre del usuario
        App app = new App();
        app.setDescripcion("");
        app.setNombre("App-" + usuario.getUsuario());
        app.setFechaCreacion(new Date());
        app.setConfiguracion(configuracion);
        session.save(app);
        // agregamos una sucursal por defecto para el usuario con el nombre de sucursal central y el nombre del usuario
        Sucursales sucursal = new Sucursales();
        sucursal.setActivo(true);
        sucursal.setApp(app);
        sucursal.setDetalle("Sucursal creada automaticamente por la app.");
        sucursal.setDireccion("");
        sucursal.setNombre("Sucursal principal -" + usuario.getUsuario());
        sucursal.setPrincipal(true);
        sucursal.setKeyid("");
//        sucursal.setUsuarios( new HashSet());
//        sucursal.getUsuarios().add(usuario);
        session.save(sucursal);
        // agregamos una persona para ese usuario
        Entidad entidad = new Entidad();
        entidad.setApp(app);
        entidad.setFechAlta(new Date());
        entidad.setTipo(TipoEntidad.PERSONA);
        session.save(entidad);
        Personas persona = usuario.getPersona();
        persona.setFechAlta(new Date());
        persona.setEntidad(entidad);
        session.save(persona);
        usuario.setApp(app);
        usuario.setTipoUsuario(1);// usuario normal
        usuario.setEstado(Boolean.TRUE);
        usuario.setRoot(Boolean.TRUE);
        usuario.setFechaCreacion(new Date());
        usuario.setRoles(new HashSet<Roles>());
        usuario.setSucursales(new HashSet<Sucursales>());
        usuario.getRoles().add(this.getRoleByName(Constants.ROLE_ADMIN));
        usuario.getSucursales().add(sucursal);
        // sacamos el gravatar segun el mail
        usuario.setKeyGravatar(SimpleEncryption.md5(usuario.getMail().trim()));

        session.save(usuario);

        // Guardamos en proveedor
        Entidad entidadEmpresa = new Entidad();
        entidadEmpresa.setApp(app);
        entidadEmpresa.setFechAlta(new Date());
        entidadEmpresa.setTipo(TipoEntidad.EMPRESA);
        session.save(entidadEmpresa);
        Empresas empresa = new Empresas();
        empresa.setEntidad(entidadEmpresa);
        empresa.setRazonSocial("Indistinto");
        empresa.setCuit("00000000000");
        empresa.setObservacion("Empresa creado automaticamente por la app - " + usuario.getUsuario());
        empresa.setTipoEmpresa(TipoEmpresa.SIN_ESPECIFICAR);
        session.save(empresa);

        // guardamos una emprsa, <- esto falta testear
        Proveedores proveedor = new Proveedores();
        proveedor.setEstado(true);
        proveedor.setObservacion("Proveedor creado automaticamente por la app - " + usuario.getUsuario());
        proveedor.setEntidad(entidadEmpresa);
        proveedor.setNroDocProveedor("00000000000");
        proveedor.setTipoDocProveedor(Doc.CUIT);
        proveedor.setTipoProveedor(TipoEmpresa.MONOTRIBUTO);
        proveedor.setApp(app);
        session.save(proveedor);

        // guardamos una familia de prueba
        String[] familiasSt = {"Gaseosas", "Comestibles", "Panaderia", "Varios", "Vinos", "Limpieza", "Fiambres", "Jugos"};
        List<Familias> familias = new ArrayList<>();
        for (java.lang.String fam : familiasSt) {
            Familias familia = new Familias();
            familia.setApp(app);
            familia.setNivel(1);
            familia.setNombre(fam);
            familia.setNombreCorto(fam.substring(0, 3).toLowerCase());
            session.save(familia);
            familias.add(familia);
        }

        // guardamos una clave para permisos de caja (654321)
        Claves clave = new Claves();
        clave.setApp(app);
        clave.setClave("e10adc3949ba59abbe56e057f20f883e");
        clave.setDetalle("Clave generada por app - " + usuario.getUsuario());
        clave.setUsuario(usuario);

        // guardamos una persona  y un usuario de test
        Entidad entidadPersona = new Entidad();
        entidadPersona.setApp(app);
        entidadPersona.setFechAlta(new Date());
        entidadPersona.setTipo(TipoEntidad.PERSONA);
        session.save(entidadPersona);
        Personas personaTest = new Personas();
        personaTest.setApellido("");
        personaTest.setNombre("Indistinto");
        personaTest.setCiudad("Jujuy");
        personaTest.setCuil("99999999999");
        personaTest.setDni("99999999");
        personaTest.setDireccion("Calle s/N");
        personaTest.setFechAlta(new Date());
        personaTest.setEntidad(entidadPersona);
        session.save(personaTest);

        Cliente cliente = new Cliente();
        cliente.setApp(app);
        cliente.setEstado(Boolean.TRUE);
        cliente.setEntidad(entidadPersona);
        cliente.setNroDocCliente("99999999");
        cliente.setTipoDocCliente(Doc.DNI);
        cliente.setTipoCliente(TipoCliente.CONSUMIDOR_FINAL);// consumidor  final por defecto
        cliente.setObservacion("Cliente de prueba");
        session.save(cliente);
        // key de la app
        String keyid = SimpleEncryption.encrypt(String.valueOf(app.getId()));
        app.setKeyid(keyid);
        app.setDirectory(BaseService.getDirFromToken(app.getId()));
        session.update(app);
        // key de la sucursal
        String keyidSucursal = SimpleEncryption.encrypt(String.valueOf(sucursal.getId()));
        sucursal.setKeyid(keyid);
        session.update(sucursal);
        // key del usuario
        String key_user = SimpleEncryption.encrypt(String.valueOf(usuario.getId()));
        usuario.setKeyUser(key_user);
        session.update(usuario);

        // ingresamos un producto de test
        ProductoPadre productoPadre = new ProductoPadre();
        productoPadre.setApp(app);
        productoPadre.setActivo(true);
        productoPadre.setDetalle("Producto padre generado por la app -  " + usuario.getUsuario());
        productoPadre.setFamilia(familias.get(0));
        productoPadre.setFechaCreacion(new Date());
        productoPadre.setNombre("Gas. Coca cola");
        productoPadre.setNombreCorto("coc col");
        session.save(productoPadre);

        Unidad unidad = session.load(Unidad.class, 5);
        Producto producto = new Producto();
        producto.setActivo(true);
        producto.setNombre("x 2.5");
        producto.setNombreFinal("Coca c x 2.5");
        producto.setContenidoNeto(new BigDecimal("2.5"));
        producto.setIva(new BigDecimal("21.0"));
        producto.setIdIva(5);
        producto.setDetalle("Producto generado por la app -  " + usuario.getUsuario());
        producto.setPrecioCosto(new BigDecimal("50.00"));
        producto.setPrecioVenta(new BigDecimal("75.00"));
        producto.setUnidad(unidad);
        producto.setPesable(Boolean.FALSE);
        producto.setIngresoCantidadManual(false);
        producto.setTipo(1);
        producto.setCodigoEspecial("COM1");
        producto.setProductoPadre(productoPadre);
        producto.setFechaCreacion(new Date());
        session.save(producto);
        producto.setCodigoBarras(new HashSet<>());
        CodigoBarras codigoBarra = new CodigoBarras();
        codigoBarra.setCodigo("777987654321");
        codigoBarra.setTipo(1);
        codigoBarra.setDescripcion("Bar code generado por la app -  " + usuario.getUsuario());
        codigoBarra.setProducto(producto);
        session.save(codigoBarra);
        return usuario;
    }

    @Override
    public Usuarios insertUserRedSocial(Usuarios usuario) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        App app = new App();
        app.setDescripcion("");
        app.setNombre("App-" + usuario.getUsuario());
        session.save(app);
        Personas persona = usuario.getPersona();
//		persona.setApp(app);
        session.save(persona);
        usuario.setApp(app);
        usuario.setEstado(Boolean.TRUE);
        usuario.setRoles(new HashSet<Roles>());
        usuario.getRoles().add(this.getRoleByName(Constants.ROLE_ADMIN));
        // sacamos el gravatar segun el mail
        session.save(usuario);
        String keyid = SimpleEncryption.encrypt(String.valueOf(app.getId()));
        app.setKeyid(keyid);
        session.update(app);
        String key_user = SimpleEncryption.encrypt(String.valueOf(usuario.getId()));
        usuario.setKeyUser(key_user);
        session.update(usuario);
        return usuario;
    }

    /**
     * Agrega un usuario desde la interfaz no publica
     *
     * @param usuario
     * @return
     * @throws ar.com.jsuper.dao.exception.BussinessException
     */
    @Override
    public Usuarios insertFromApp(Usuarios usuario) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        /*###########################Control por APP############################*/
        usuario.setApp(session.load(App.class, TenantContext.getCurrentTenant()));
        usuario.setEstado(Boolean.TRUE);
        /*######################################################################*/
        // sacamos el gravatar segun el mail
        usuario.setKeyGravatar(SimpleEncryption.md5(usuario.getMail().trim()));
        session.save(usuario);
        String key_user = SimpleEncryption.encrypt(String.valueOf(usuario.getId()));
        usuario.setKeyUser(key_user);
        session.update(usuario);
        return usuario;
    }

    /**
     * Guarda registro para recuperar pass
     *
     */
    @Override
    public RecuperacionPass saveRecuperarPass(RecuperacionPass recuperacionPass) {
        Session session = sessionFactory.getCurrentSession();
        session.save(recuperacionPass);
        return recuperacionPass;
    }
    /**
     * Modifica registro para recuperar pass
     *
     */
    @Override
    public RecuperacionPass updateRecuperarPass(RecuperacionPass recuperacionPass) {
        Session session = sessionFactory.getCurrentSession();
        session.update(recuperacionPass);
        return recuperacionPass;
    }

    /**
     * Devuelve el registro o vacio caso contrario
     *
     */
    @Override
    public RecuperacionPass getRecuperacionPasswordFromToken(String token) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(RecuperacionPass.class, "rec");
        Criterion c1 = Restrictions.eq("token", token);
        c.add(c1);
        try {
            return (RecuperacionPass) c.uniqueResult();
        } catch (HibernateException he) {
            return null;
        }
    }

    /**
     * Modificar un usuario desde la interfaz no publica
     *
     * @return
     * @throws ar.com.jsuper.dao.exception.BussinessException
     */
    @Override
    public Usuarios updateFromApp(Usuarios usuarioOld, Usuarios usuarioNew) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        usuarioOld = this.getObject(usuarioOld, usuarioNew);
        if (usuarioOld.getTipoLogo() == "0") {
            usuarioOld.setKeyGravatar(SimpleEncryption.md5(usuarioOld.getMail().trim()));
        }
        session.update(usuarioOld);
        return usuarioOld;
    }

    /**
     * Se verifica si una persona ya existe como usuario
     *
     * @return
     * @throws ar.com.jsuper.dao.exception.BussinessException
     */
    @Override
    public boolean isExistUserFromPersona(Personas persona) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Usuarios.class, "usuario");
        Criterion c1 = Restrictions.eq("persona", persona);
        c.add(c1);
        try {
            return !Objects.isNull(c.uniqueResult());
        } catch (HibernateException e) {// en caso con resultado mas de una  o uno que no vale
            return true;
        }
    }

    @Override
    public Roles getRoleByName(String role) {
        Session session = sessionFactory.getCurrentSession();
        Criteria c = session.createCriteria(Roles.class, "rol");
        Criterion c1 = Restrictions.eq("nombre", role);
        c.add(c1);
        try {
            return (Roles) c.uniqueResult();
        } catch (HibernateException e) {// en caso con resultado mas de una  o uno que no vale
            return null;
        }
    }

    @Override
    public void saveLogUser(Usuarios usuario) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        LogUser logUser = new LogUser();
        logUser.setUsuario(usuario);
        logUser.setFechaIngreso(new Date());
        session.save(logUser);
    }

    @Override
    public LogUser getBeforesLastLogUser(Integer idUser) throws BussinessException {
        Session session = sessionFactory.getCurrentSession();
        Usuarios usuario = session.load(Usuarios.class, idUser);
        Criteria c = session.createCriteria(LogUser.class, "log");
        Criterion c1 = Restrictions.eq("usuario", usuario);
        c.add(c1);
        c.setMaxResults(2);
        c.addOrder(Order.desc("id"));
        List<LogUser> lista = c.list();
        if (lista.size() >= 2) {
            return lista.get(1);
        } else {
            return null;
        }
    }

    @Override
    public Pagination<Usuarios> getUsuariosBypage(FilterUsuarios filterUsuarios, int page, int pageSize, String fieldOrder, boolean reverse) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaUsuario = session.createCriteria(Usuarios.class, "usuario");
        Projection idCountProjection = Projections.countDistinct("id");
        criteriaUsuario.setProjection(idCountProjection);
        Criteria criteriaPersonas = criteriaUsuario.createCriteria("usuario.persona", "persona", JoinType.LEFT_OUTER_JOIN);
        criteriaPersonas.setFetchMode("precios", FetchMode.JOIN);
        criteriaUsuario.setFetchMode("roles", FetchMode.JOIN);
//        criteriaPersonas.createAlias("persona.domicilios", "domicilios", JoinType.LEFT_OUTER_JOIN);
//        criteriaPersonas.createAlias("persona.contactos", "contactos", JoinType.LEFT_OUTER_JOIN);
//        criteriaPersonas.createAlias("persona.telefonos", "telefonos", JoinType.LEFT_OUTER_JOIN);
        Integer activo = filterUsuarios.getActivo();
        Integer root = filterUsuarios.getRoot();
        Criterion c1;
        if (filterUsuarios.getApellido() != null && !filterUsuarios.getApellido().trim().equals("")) {
            c1 = Restrictions.like("persona.apellido", filterUsuarios.getApellido(), MatchMode.ANYWHERE);
            criteriaPersonas.add(c1);
        }
        if (filterUsuarios.getNombre() != null && !filterUsuarios.getNombre().trim().equals("")) {
            c1 = Restrictions.like("persona.nombre", filterUsuarios.getNombre(), MatchMode.ANYWHERE);
            criteriaPersonas.add(c1);
        }
        if (filterUsuarios.getUsuario() != null && !filterUsuarios.getUsuario().trim().equals("")) {
            c1 = Restrictions.like("usuario.usuario", filterUsuarios.getUsuario(), MatchMode.ANYWHERE);
            criteriaPersonas.add(c1);
        }
        // nos fijamos el id 
        if (!Objects.isNull(filterUsuarios.getId())) {
            if (filterUsuarios.getId() > 0) {
                criteriaUsuario.add(Restrictions.eq("id", filterUsuarios.getId()));
            }
        }
        // restriccion para activos o no activos
        if (activo != null && !activo.equals("") && activo != 2) {
            Criterion cActivo = Restrictions.eq("usuario.estado", (activo == 1));
            criteriaUsuario.add(cActivo);
        }
        // restriccion para usuarios root
        if (root != null && !root.equals("") && root != 2) {
            Criterion cRoot = Restrictions.eq("usuario.root", (root == 1));
            criteriaUsuario.add(cRoot);
        }
        /*###########################Control por APP############################*/
        criteriaUsuario.setFetchMode("usuario.app", FetchMode.JOIN);
        c1 = Restrictions.eq("app.id", TenantContext.getCurrentTenant());
        criteriaUsuario.add(c1);
        /*######################################################################*/
        Integer totalResultCount = ((Long) criteriaUsuario.uniqueResult()).intValue();

        if (reverse) {
            criteriaUsuario.addOrder(Order.asc(fieldOrder.trim()));
        } else {
            criteriaUsuario.addOrder(Order.desc(fieldOrder.trim()));
        }
        criteriaUsuario.setProjection(Projections.distinct(Projections.property("id")));
        criteriaUsuario.setFirstResult((page - 1) * pageSize);
        criteriaUsuario.setMaxResults(pageSize);
        List uniqueSubList = criteriaUsuario.list();
        criteriaUsuario.setProjection(null);
        criteriaUsuario.setFirstResult(0);
        criteriaUsuario.setMaxResults(Integer.MAX_VALUE);
        criteriaUsuario.add(Restrictions.in("id", uniqueSubList));
        criteriaUsuario.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        ArrayList<Usuarios> lista = null;
        if (totalResultCount > 0) {
            lista = new ArrayList<>(criteriaUsuario.list());
        }
        Pagination<Usuarios> pagination = new Pagination<>();
        pagination.setData(lista);
        pagination.setTotal(totalResultCount);
        pagination.setPage(page);
        pagination.setPageSize(pageSize);
        return pagination;
    }

    @Override
    public Usuarios get(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaUsuario = session.createCriteria(Usuarios.class, "usuario");
        Criteria criteriaPersonas = criteriaUsuario.createCriteria("usuario.persona", "persona", JoinType.LEFT_OUTER_JOIN);
        criteriaUsuario.setFetchMode("roles", FetchMode.JOIN);
        criteriaUsuario.setFetchMode("sucursales", FetchMode.JOIN);
        criteriaUsuario.add(Restrictions.eq("id", id));
        Usuarios usuario = (Usuarios) criteriaUsuario.uniqueResult();
        return usuario;
    }

    @Override
    public Usuarios getMin(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaUsuario = session.createCriteria(Usuarios.class, "usuario");
        Criteria criteriaPersonas = criteriaUsuario.createCriteria("usuario.persona", "persona", JoinType.LEFT_OUTER_JOIN);
        criteriaUsuario.add(Restrictions.eq("id", id));
        Usuarios usuario = (Usuarios) criteriaUsuario.uniqueResult();
        return usuario;
    }

    @Override
    public Usuarios getByIdLogged() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaUsuario = session.createCriteria(Usuarios.class, "usuario");
        criteriaUsuario.setFetchMode("roles", FetchMode.JOIN);
        criteriaUsuario.add(Restrictions.eq("id", TenantContext.getCurrentIdUser()));
        Usuarios usuario = (Usuarios) criteriaUsuario.uniqueResult();
        return usuario;
    }

    @Override
    public Usuarios getObject(Usuarios usuarioOld, Usuarios usuarioNew) {
        if (Objects.isNull(usuarioOld)) {
            usuarioOld = new Usuarios();
        }
        usuarioOld.setTipo(usuarioNew.getTipo());
        usuarioOld.setObservacion(usuarioNew.getObservacion());
        usuarioOld.setMail(usuarioNew.getMail().trim());
        usuarioOld.setUsuario(usuarioNew.getUsuario().trim());
        usuarioOld.setPersona(usuarioNew.getPersona());
        usuarioOld.setKeyGravatar(usuarioNew.getKeyGravatar());
        usuarioOld.setPassword(usuarioNew.getPassword());
        usuarioOld.setLogo(usuarioNew.getLogo());
        usuarioOld.setAltLogo(usuarioNew.getAltLogo());
        usuarioOld.setTipoLogo(usuarioNew.getTipoLogo());
        return usuarioOld;
    }
}
