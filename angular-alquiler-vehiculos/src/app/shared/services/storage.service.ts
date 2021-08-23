import * as CryptoJS from 'crypto-js';
 
import { Injectable } from '@angular/core';
import { environment } from "../../../environments/environment";
 
const SecureStorage = require('secure-web-storage');
const SECRET_KEY = environment.SECRET_KEY;
@Injectable({
  providedIn: 'root'
})
export class StorageService {
 
  constructor() { }
  public secureStorage = new SecureStorage(localStorage, {
    hash: function hash(key: any) {
      key = CryptoJS.SHA256(key);
 
      return key.toString();
    },
    encrypt: function encrypt(data: any) {
      data = CryptoJS.AES.encrypt(data, SECRET_KEY);
 
      data = data.toString();
 
      return data;
    },
    decrypt: function decrypt(data: any) {
      data = CryptoJS.AES.decrypt(data, SECRET_KEY);
 
      data = data.toString(CryptoJS.enc.Utf8);
 
      return data;
    }
  });
 
}