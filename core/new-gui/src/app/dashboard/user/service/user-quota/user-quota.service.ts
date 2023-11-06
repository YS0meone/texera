import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppSettings } from "src/app/common/app-setting";
import { File, Workflow, MongoExecution } from "src/app/common/type/user";

export const USER_BASE_URL = `${AppSettings.getApiEndpoint()}/quota`;
export const USER_CREATED_FILES = `${USER_BASE_URL}/uploaded_files`;
export const USER_CREATED_WORKFLOWS = `${USER_BASE_URL}/created_workflows`;
export const USER_ACCESS_WORKFLOWS = `${USER_BASE_URL}/access_workflows`;
export const USER_ACCESS_FILES = `${USER_BASE_URL}/access_files`;
export const USER_MONGODB_SIZE = `${USER_BASE_URL}/mongodb_size`;
export const USER_DELETE_MONGODB_COLLECTION_NAME = `${USER_BASE_URL}/deleteCollection`;

@Injectable({
  providedIn: "root",
})
export class UserQuotaService {
  constructor(private http: HttpClient) {}

  public getUploadedFiles(uid: number): Observable<ReadonlyArray<File>> {
    return this.http.get<ReadonlyArray<File>>(`${USER_CREATED_FILES}`);
  }

  public getCreatedWorkflows(uid: number): Observable<ReadonlyArray<Workflow>> {
    return this.http.get<ReadonlyArray<Workflow>>(`${USER_CREATED_WORKFLOWS}`);
  }

  public getAccessFiles(uid: number): Observable<ReadonlyArray<number>> {
    return this.http.get<ReadonlyArray<number>>(`${USER_ACCESS_FILES}`);
  }

  public getAccessWorkflows(uid: number): Observable<ReadonlyArray<number>> {
    return this.http.get<ReadonlyArray<number>>(`${USER_ACCESS_WORKFLOWS}`);
  }

  public getMongoDBs(uid: number): Observable<ReadonlyArray<MongoExecution>> {
    return this.http.get<ReadonlyArray<MongoExecution>>(`${USER_MONGODB_SIZE}`);
  }

  public deleteMongoDBCollection(collectionName: string): Observable<void> {
    console.log(`${USER_DELETE_MONGODB_COLLECTION_NAME}/${collectionName}`);
    return this.http.delete<void>(`${USER_DELETE_MONGODB_COLLECTION_NAME}/${collectionName}`);
  }
}
