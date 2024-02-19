import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Product } from './product.class';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

    private static productslist: Product[] = null;
    private products$: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);

    constructor(private http: HttpClient) { }

      private productsUrl = 'http://localhost:8080/products'; // Define the products URL

    getProducts(): Observable<Product[]> {
        return this.http.get<Product[]>(this.productsUrl);
    }

    create(prod: Product): Observable<Product[]> {
        return this.http.post<Product[]>(this.productsUrl, prod);
    }

    update(prod: Product): Observable<Product> {
        const url = `${this.productsUrl}/${prod.id}`;
        return this.http.patch<Product>(url, prod).pipe(
            tap(() => {
                // Update the local products list if needed
                ProductsService.productslist = ProductsService.productslist.map(element => {
                if (element.id === prod.id) {
                    return {
                    ...element,
                    name: prod.name,
                    category: prod.category,
                    code: prod.code,
                    description: prod.description,
                    image: prod.image,
                    inventoryStatus: prod.inventoryStatus,
                    price: prod.price,
                    quantity: prod.quantity,
                    rating: prod.rating
                    };
                }
                return element;
                });
                this.products$.next(ProductsService.productslist);
            })
        );
    }


    delete(id: number): Observable<Product[]>{
        const url = `${this.productsUrl}/${id}`;
        return this.http.delete<Product[]>(url);
    }
}