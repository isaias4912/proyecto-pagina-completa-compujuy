/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.jsuper.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "2299";

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http  .csrf().disable()
                .anonymous().disable()
				.authorizeRequests()
				.antMatchers("/api/v1/auth/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers("/api/v1/ventas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers("/api/v1/presupuesto/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/util/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/cajas/data/caja/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers(HttpMethod.POST, "/api/v1/cajas").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers(HttpMethod.GET, "/api/v1/usuarios/min/{\\d+}").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers("/api/v1/cajas/apertura/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers("/api/v1/cajas/cierre/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers("/api/v1/cajas/movimiento/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers("/api/v1/cajas/anulaventa").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers("/api/v1/cajas/resumencaja/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers("/api/v1/cajas/clave/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO")
				.antMatchers("/api/v1/clientes/existclienteorpersona/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/clientes/pagination/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/clientes/{\\d+}/mails/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/clientes/{\\d+}/min/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/entidad/all/emails/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/entidad/clientes/pagination/multiple/full/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers(HttpMethod.POST,"/api/v1/clientes/entidad").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/mailing/ventas/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/reportes/ventas/cbte/pdf/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/productos/codigobarra/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/productos/codigobarraorname").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/productos/pagination/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/productos/ids/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/productos/{\\d+}").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/usuarios/menu").hasAnyAuthority("ROLE_ADMIN", "ROLE_GERENTE", "ROLE_CAJERO", "ROLE_VENDEDOR")
				.antMatchers("/api/v1/sys/**").hasAnyAuthority("ROLE_ADMIN")
				.antMatchers("/api/**").hasAnyAuthority("ROLE_GERENTE")
				.antMatchers("/api/**").hasAnyAuthority("ROLE_ADMIN");

	}

}
