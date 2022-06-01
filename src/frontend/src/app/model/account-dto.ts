export interface AccountDto {
  accountType: string;
  accountNumber?: number
  state: boolean;
  withdrawLimit: number;
  customerIdentification: string;
  balance: number;
}
