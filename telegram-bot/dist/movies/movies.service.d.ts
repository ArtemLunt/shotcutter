import { HttpService } from '@nestjs/axios';
import { Observable } from 'rxjs';
export declare class MoviesService {
    private _http;
    constructor(_http: HttpService);
    lookup(key: string): Observable<any[]>;
}
