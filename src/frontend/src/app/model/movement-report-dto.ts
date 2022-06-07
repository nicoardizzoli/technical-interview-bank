export interface MovementReportDto {
  date: string
  movementType: string
  holderFullName: string
  accountNumber: number;
  accountType: string;
  balance: number;
  estado: boolean;
  amount: number;
  availableBalance: number;
}
