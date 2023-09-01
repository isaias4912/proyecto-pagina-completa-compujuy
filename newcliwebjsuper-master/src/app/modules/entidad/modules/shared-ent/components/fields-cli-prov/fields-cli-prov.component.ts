import { ComponentPage } from '../../../../../../core/component-page';

export abstract class FieldsCliProv extends ComponentPage {
    public enableNroDoc = true;

    public dataOptions = {
        tipos: [],
        temptipos: [
            { value: 'SIN_ESPECIFICAR', name: "Sin especificar" },
            { value: 'MONOTRIBUTO', name: "Monotributo" },
            { value: 'RESPONSABLE_INSCRIPTO', name: "Responsable Inscripto" },
            { value: 'EXCENTO', name: "Excento" },
            { value: 'CONSUMIDOR_FINAL', name: "Condumidor Final" },
        ],
        tempDocs: [
            { id: 80, value: 'CUIT', disabled: false },
            { id: 86, value: 'CUIL', disabled: false },
            { id: 87, value: 'CDI', disabled: false },
            { id: 89, value: 'LE', disabled: false },
            { id: 90, value: 'LC', disabled: false },
            { id: 96, value: 'DNI', disabled: false }
        ],
        docs: []
    };
    loadTipos(optTipos: Array<string> = null) {
        this.dataOptions.tipos = [];
        if (optTipos) {
            this.dataOptions.temptipos.forEach(optA => {
                optTipos.forEach(optB => {
                    if (optA.value == optB) {
                        this.dataOptions.tipos.push(optA);
                    }
                });
            });
        } else {
            this.dataOptions.tipos = this.dataOptions.temptipos;
        }
    }
    loadDocs(optDoc: Array<string> = null) {
        this.dataOptions.docs = [];
        if (optDoc) {
            this.dataOptions.tempDocs.forEach(optA => {
                optDoc.forEach(optB => {
                    if (optA.value == optB) {
                        this.dataOptions.docs.push(optA);
                    }
                });
            });
        } else {
            this.dataOptions.docs = this.dataOptions.tempDocs;
        }
    }
}

