
export class ObjectValidator {
  constructor() {

  }
  isValid(data: any, fields: Array<string> = []): boolean {
    let valid = true;
    fields.forEach(item => {
      if (valid) {
        if (!data.hasOwnProperty(item)) {
          valid = false;
        }
      }
    });
    return valid;
  }
  isValidEmpresa(data: any, fields: Array<string> = ['razonSocial', 'id']): boolean {
    let valid: boolean = false;
    if (data.hasOwnProperty('entidad')) {
      let entidad = data.entidad
      if (entidad.hasOwnProperty('empresa')) {
        if (entidad.empresa) {
          let empresa = entidad.empresa
          valid = true;
          fields.forEach(item => {
            if (valid) {
              if (!empresa.hasOwnProperty(item)) {
                valid = false;
              }
            }
          });
        }
      }
    }
    return valid;
  }
  isValidPersona(data: any, fields: Array<string> = ['apellido', 'nombre', 'id']): boolean {
    let valid: boolean = false;
    if (data.hasOwnProperty('entidad')) {
      let entidad = data.entidad
      if (entidad.hasOwnProperty('persona')) {
        if (entidad.persona) {
          let persona = entidad.persona
          valid = true;
          fields.forEach(item => {
            if (valid) {
              if (!persona.hasOwnProperty(item)) {
                valid = false;
              }
            }
          });
        }
      }

    }
    return valid;
  }
}
