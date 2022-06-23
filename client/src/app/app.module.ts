import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxsStoragePluginModule } from '@ngxs/storage-plugin';
import { AppRoutingModule } from '@sc/app-routing.module';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from '@sc/app.component';
import { environment } from '@sc/environments';
import { SharedModule } from '@sc/shared';
import { NgModule } from '@angular/core';
import { NgxsModule } from '@ngxs/store';
import { ThemeState } from '@sc/theme';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserAnimationsModule,
    NgxsModule.forRoot([ThemeState], {
      developmentMode: !environment.production
    }),
    NgxsStoragePluginModule.forRoot({
      key: ThemeState
    }),
    AppRoutingModule,
    BrowserModule,
    SharedModule,
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
