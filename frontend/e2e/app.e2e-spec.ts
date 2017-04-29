import { DebtManagementPage } from './app.po';

describe('debt-management App', function() {
  let page: DebtManagementPage;

  beforeEach(() => {
    page = new DebtManagementPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
