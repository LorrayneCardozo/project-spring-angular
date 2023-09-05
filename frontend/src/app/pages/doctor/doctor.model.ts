import { HealthCenter } from "../health-center/health-center.model";

export interface Doctor {
    id: number;
    name: string;
    specialty: string;
    cpf: string;
    crm: string;
    phone: string;
    healthCenter: HealthCenter;
    availabilities: string[];
}
  