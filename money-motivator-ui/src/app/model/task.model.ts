export enum TaskStatus {
  NEW = 'NEW',
  IN_PROGRESS = 'IN_PROGRESS',
  DONE = 'DONE',
  VERIFIED = 'VERIFIED',
  PAID = 'PAID'
}

export interface TaskModel {
  id: number;
  name: string;
  description: string;
  price: number;
  status: TaskStatus;
  authorEmail: string;
  assigneeEmail: string;
  projectId: number;
}
