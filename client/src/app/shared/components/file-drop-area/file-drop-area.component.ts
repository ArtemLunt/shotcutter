import {Component, ChangeDetectionStrategy, Input, HostListener, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'sc-file-drop-area',
  templateUrl: './file-drop-area.component.html',
  styleUrls: ['./file-drop-area.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FileDropAreaComponent {

  @Input() labelText: string;
  @Output() fileLoaded: EventEmitter<File>;

  isDragOver: boolean;

  constructor() {
    this.fileLoaded = new EventEmitter();
  }

  @HostListener('drop', ['$event?.dataTransfer?.files'])
  onFileLoad(files: FileList): void {
    this.isDragOver = false;
    const file = files?.item(0);
    if (!file) {
      return;
    }
    this.fileLoaded.emit(file);
  }

}
