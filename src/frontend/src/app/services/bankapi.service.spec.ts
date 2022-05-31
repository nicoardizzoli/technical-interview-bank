import { TestBed } from '@angular/core/testing';

import { BankapiService } from './bankapi.service';

describe('BankapiService', () => {
  let service: BankapiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BankapiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
