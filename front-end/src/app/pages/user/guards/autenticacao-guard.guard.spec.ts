import { TestBed } from '@angular/core/testing';

import { AutenticacaoGuardGuard } from './autenticacao-guard.guard';

describe('AutenticacaoGuardGuard', () => {
  let guard: AutenticacaoGuardGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(AutenticacaoGuardGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
